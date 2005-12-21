package org.apache.maven.continuum.web.action;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.continuum.Continuum;
import org.apache.maven.continuum.ContinuumException;
import org.apache.maven.continuum.model.project.Project;
import org.apache.maven.continuum.project.builder.ContinuumProjectBuildingResult;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import com.opensymphony.xwork.ActionSupport;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Nick Gonzalez
 * @version $Id$
 */
public class AddMavenTwoProjectAction 
    extends ActionSupport  
{
    private Continuum continuum;

    private String m2PomUrl;

    private File m2PomFile;

    private String m2Pom = null;

    public String execute()
    {
        if ( !StringUtils.isEmpty( m2PomUrl ) )
        {
            m2Pom = m2PomUrl;
        }
        else
        {
            if ( m2PomFile != null )
            {
                try
                {
                    m2Pom = m2PomFile.toURL().toString();
                }
                catch ( MalformedURLException e )
                {
                    System.out.println( "aaa" );
                    return INPUT;
                }
            }
            else
            {
                System.out.println( "bbb" );
                return INPUT;
            }
        }

        ContinuumProjectBuildingResult result = null;

        try
        {
            result = continuum.addMavenTwoProject( m2Pom );
        }
        catch ( ContinuumException e )
        {
            System.out.println( "ccc" );
            return INPUT;
        }
        
        if( result.getWarnings().size() > 0 )
        {
            addActionMessage( result.getWarnings().toArray().toString() );

            return INPUT;
        }

        return SUCCESS;
    }

    public String doDefault()
    {
        return INPUT;
    }

    public File getM2PomFile() {
        return m2PomFile;
    }

    public void setM2PomFile( File pomFile )
    {
        m2PomFile = pomFile;
    }

    public String getM2PomUrl()
    {
        return m2PomUrl;
    }

    public void setM2PomUrl( String pomUrl )
    {
        m2PomUrl = pomUrl;
    }
}
