package org.apache.maven.continuum.release.executors;

/*
 * Copyright 2006 The Apache Software Foundation.
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

import org.apache.maven.continuum.release.tasks.PrepareReleaseProjectTask;
import org.apache.maven.continuum.release.tasks.PerformReleaseProjectTask;
import org.apache.maven.plugins.release.config.ReleaseDescriptor;
import org.apache.maven.scm.manager.NoSuchScmProviderException;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.apache.maven.scm.repository.ScmRepositoryException;
import org.apache.maven.scm.ScmFileSet;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.taskqueue.Task;
import org.codehaus.plexus.taskqueue.execution.TaskExecutor;

import java.io.File;

/**
 * @author Edwin Punzalan
 */
public class ReleaseTaskExecutorTest
    extends PlexusTestCase
{
    private ScmManager scmManager;

    private TaskExecutor prepareExec;

    private TaskExecutor performExec;

    protected void setUp()
        throws Exception
    {
        super.setUp();

        if ( scmManager == null )
        {
            scmManager = (ScmManager) lookup( "org.apache.maven.scm.manager.ScmManager" );
        }

        if ( prepareExec == null )
        {
            prepareExec = (TaskExecutor) lookup( TaskExecutor.class.getName(), "prepare-release" );
        }

        if ( performExec == null )
        {
            performExec = (TaskExecutor) lookup( TaskExecutor.class.getName(), "perform-release" );
        }
    }

    public void testReleaseSimpleProject()
        throws Exception
    {
        String scmPath = new File( getBasedir(), "target/scm-src" ).getAbsolutePath().replace( '\\', '/' );
        File workDir = new File( getBasedir(), "target/test-classes/work-dir" );
        FileUtils.deleteDirectory( workDir );
        File testDir = new File( getBasedir(), "target/test-classes/test-dir" );
        FileUtils.deleteDirectory( testDir );

        ReleaseDescriptor descriptor = new ReleaseDescriptor();
        descriptor.setInteractive( false );
        descriptor.setScmSourceUrl( "scm:svn:file://localhost/" + scmPath + "/trunk" );
        descriptor.setWorkingDirectory( workDir.getAbsolutePath() );

        ScmRepository repository = getScmRepositorty( descriptor.getScmSourceUrl() );
        ScmFileSet fileSet = new ScmFileSet( workDir );
        scmManager.getProviderByRepository( repository ).checkOut( repository, fileSet, null );

        String pom = FileUtils.fileRead( new File( workDir, "pom.xml" ) );
        assertTrue( "Test dev version", pom.indexOf( "<version>1.0-SNAPSHOT</version>" ) > 0 );

        prepareExec.executeTask( getPrepareTask( "testRelease", descriptor ) );

        pom = FileUtils.fileRead( new File( workDir, "pom.xml" ) );
        assertTrue( "Test version increment", pom.indexOf( "<version>1.1-SNAPSHOT</version>" ) > 0 );

        repository = getScmRepositorty( "scm:svn:file://localhost/" + scmPath + "/tags/test-artifact-1.0" );
        fileSet = new ScmFileSet( testDir );
        scmManager.getProviderByRepository( repository ).checkOut( repository, fileSet, null );

        pom = FileUtils.fileRead( new File( testDir, "pom.xml" ) );
        assertTrue( "Test released version", pom.indexOf( "<version>1.0</version>" ) > 0 );
    }

    public void testReleaseSimpleProjectWithNextVersion()
        throws Exception
    {
        String scmPath = new File( getBasedir(), "target/scm-src" ).getAbsolutePath().replace( '\\', '/' );
        File workDir = new File( getBasedir(), "target/test-classes/work-dir" );
        FileUtils.deleteDirectory( workDir );
        File testDir = new File( getBasedir(), "target/test-classes/test-dir" );
        FileUtils.deleteDirectory( testDir );

        ReleaseDescriptor descriptor = new ReleaseDescriptor();
        descriptor.setInteractive( false );
        descriptor.setScmSourceUrl( "scm:svn:file://localhost/" + scmPath + "/trunk" );
        descriptor.setWorkingDirectory( workDir.getAbsolutePath() );
        descriptor.mapReleaseVersion( "test-group:test-artifact", "2.0" );
        descriptor.mapDevelopmentVersion( "test-group:test-artifact", "2.1-SNAPSHOT" );

        ScmRepository repository = getScmRepositorty( descriptor.getScmSourceUrl() );
        ScmFileSet fileSet = new ScmFileSet( workDir );
        scmManager.getProviderByRepository( repository ).checkOut( repository, fileSet, null );

        String pom = FileUtils.fileRead( new File( workDir, "pom.xml" ) );
        assertTrue( "Test dev version", pom.indexOf( "<version>1.1-SNAPSHOT</version>" ) > 0 );

        prepareExec.executeTask( getPrepareTask( "testRelease", descriptor ) );

        pom = FileUtils.fileRead( new File( workDir, "pom.xml" ) );
        assertTrue( "Test version increment", pom.indexOf( "<version>2.1-SNAPSHOT</version>" ) > 0 );

        repository = getScmRepositorty( "scm:svn:file://localhost/" + scmPath + "/tags/test-artifact-2.0" );
        fileSet = new ScmFileSet( testDir );
        scmManager.getProviderByRepository( repository ).checkOut( repository, fileSet, null );

        pom = FileUtils.fileRead( new File( testDir, "pom.xml" ) );
        assertTrue( "Test released version", pom.indexOf( "<version>2.0</version>" ) > 0 );

        performExec.executeTask( getPerformTask( "testRelease", descriptor, new File( getBasedir(), "target/test-classes/build-dir" ) ) );
    }

    private Task getPrepareTask( String releaseId, ReleaseDescriptor descriptor )
    {
        Task task = new PrepareReleaseProjectTask( releaseId, descriptor, null );

        return task;
    }

    private Task getPerformTask( String releaseId, ReleaseDescriptor descriptor, File buildDir )
    {
        Task task = new PerformReleaseProjectTask( releaseId, descriptor, buildDir, "package", true, null );

        return task;
    }

    private ScmRepository getScmRepositorty( String scmUrl )
        throws ScmRepositoryException, NoSuchScmProviderException
    {
        ScmRepository repository = scmManager.makeScmRepository( scmUrl.trim() );

        repository.getProviderRepository().setPersistCheckout( true );

        return repository;
    }
}