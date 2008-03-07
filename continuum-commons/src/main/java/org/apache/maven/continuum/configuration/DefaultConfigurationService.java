package org.apache.maven.continuum.configuration;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.IOException;

import org.apache.maven.continuum.model.project.Schedule;
import org.apache.maven.continuum.model.system.SystemConfiguration;
import org.apache.maven.continuum.store.ContinuumStore;
import org.apache.maven.continuum.store.ContinuumStoreException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.util.FileUtils;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id$
 * @plexus.component role="org.apache.maven.continuum.configuration.ConfigurationService"
 */
public class DefaultConfigurationService
    extends AbstractLogEnabled
    implements ConfigurationService
{

    // when adding requirement the template in application.xml must be updated CONTINUUM-1207

    /**
     * @plexus.configuration default-value="${plexus.home}"
     */
    private File applicationHome;


    /**
     * @plexus.requirement role-hint="jdo"
     */
    private ContinuumStore store;

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private SystemConfiguration systemConf;

    private boolean loaded = false;
    

    
    
    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------



    public File getApplicationHome()
    {
        return applicationHome;
    }

    public void setInitialized( boolean initialized )
    {
        systemConf.setInitialized( initialized );
    }

    public boolean isInitialized()
    {
        return systemConf.isInitialized();
    }

    public String getUrl()
    {
        if ( systemConf.getBaseUrl() != null )
        {
            return systemConf.getBaseUrl();
        }
        else
        {
            return "";
        }
    }

    public void setUrl( String url )
    {
        systemConf.setBaseUrl( url );
    }

    public File getBuildOutputDirectory()
    {
        return getFile( systemConf.getBuildOutputDirectory() );
    }

    public void setBuildOutputDirectory( File buildOutputDirectory )
    {
        File f = buildOutputDirectory;
        try
        {
            f = f.getCanonicalFile();
        }
        catch ( IOException e )
        {
        }
        systemConf.setBuildOutputDirectory( f.getAbsolutePath() );
    }

    public File getWorkingDirectory()
    {
        return getFile( systemConf.getWorkingDirectory() );
    }

    public void setWorkingDirectory( File workingDirectory )
    {
        File f = workingDirectory;
        try
        {
            f = f.getCanonicalFile();
        }
        catch ( IOException e )
        {
        }

        systemConf.setWorkingDirectory( f.getAbsolutePath() );
    }

    public File getDeploymentRepositoryDirectory()
    {
        return getFile( systemConf.getDeploymentRepositoryDirectory() );
    }

    public void setDeploymentRepositoryDirectory( File deploymentRepositoryDirectory )
    {
        systemConf.setDeploymentRepositoryDirectory(
            deploymentRepositoryDirectory != null ? deploymentRepositoryDirectory.getAbsolutePath() : null );
    }

    public String getBuildOutput( int buildId, int projectId )
        throws ConfigurationException
    {
        File file = getBuildOutputFile( buildId, projectId );

        try
        {
            if ( file.exists() )
            {
                return FileUtils.fileRead( file.getAbsolutePath() );
            }
            else
            {
                return "There are no output for this build.";
            }
        }
        catch ( IOException e )
        {
            getLogger().warn( "Error reading build output for build '" + buildId + "'.", e );

            return null;
        }
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------


    public File getBuildOutputDirectory( int projectId )
    {
        File dir = new File( getBuildOutputDirectory(), Integer.toString( projectId ) );

        try
        {
            dir = dir.getCanonicalFile();
        }
        catch ( IOException e )
        {
        }

        return dir;
    }

    public File getTestReportsDirectory( int buildId, int projectId )
        throws ConfigurationException
    {
        File ouputDirectory = getBuildOutputDirectory( projectId );

        return new File(
            ouputDirectory.getPath() + File.separatorChar + buildId + File.separatorChar + "surefire-reports" );

    }

    public File getBuildOutputFile( int buildId, int projectId )
        throws ConfigurationException
    {
        File dir = getBuildOutputDirectory( projectId );

        if ( !dir.exists() && !dir.mkdirs() )
        {
            throw new ConfigurationException(
                "Could not make the build output directory: " + "'" + dir.getAbsolutePath() + "'." );
        }

        return new File( dir, buildId + ".log.txt" );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    public File getFile( String filename )
    {
        if ( filename == null )
        {
            return null;
        }

        File f = null;

        if ( filename != null && filename.length() != 0 )
        {
            f = new File( filename );

            if ( !f.isAbsolute() )
            {
                f = new File( applicationHome, filename );
            }
        }

        try
        {
            return f.getCanonicalFile();
        }
        catch ( IOException e )
        {
            return f;
        }
    }

    // ----------------------------------------------------------------------
    // Load and Store
    // ----------------------------------------------------------------------

    public boolean isLoaded()
    {
        return loaded;
    }

    public void load()
        throws ConfigurationLoadingException
    {
        try
        {
            systemConf = store.getSystemConfiguration();

            if ( systemConf == null )
            {
                systemConf = new SystemConfiguration();

                systemConf = store.addSystemConfiguration( systemConf );
            }

            loaded = true;
        }
        catch ( ContinuumStoreException e )
        {
            throw new ConfigurationLoadingException( "Error reading configuration from database.", e );
        }
    }

    public void store()
        throws ConfigurationStoringException
    {
        try
        {
            store.updateSystemConfiguration( systemConf );
        }
        catch ( ContinuumStoreException e )
        {
            throw new ConfigurationStoringException( "Error writting configuration to database.", e );
        }
    }
    
    public Schedule getDefaultSchedule()
        throws ContinuumStoreException, ConfigurationLoadingException
    {
        // Schedule
        Schedule defaultSchedule = store.getScheduleByName( DEFAULT_SCHEDULE_NAME );

        if ( defaultSchedule == null )
        {
            defaultSchedule = createDefaultSchedule();

            defaultSchedule = store.addSchedule( defaultSchedule );
        }

        return defaultSchedule;
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private Schedule createDefaultSchedule()
        throws ConfigurationLoadingException
    {
        
        getLogger().info( "create Default Schedule" );
        
        Schedule schedule = new Schedule();

        schedule.setName( DEFAULT_SCHEDULE_NAME );

        if ( systemConf == null )
        {
            this.load();
        }

        schedule.setDescription( systemConf.getDefaultScheduleDescription() );

        schedule.setCronExpression( systemConf.getDefaultScheduleCronExpression() );

        schedule.setActive( true );

        return schedule;
    }    

}