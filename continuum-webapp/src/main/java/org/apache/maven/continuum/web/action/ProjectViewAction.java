package org.apache.maven.continuum.web.action;

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

import org.apache.maven.continuum.ContinuumException;
import org.apache.maven.continuum.model.project.BuildResult;
import org.apache.maven.continuum.model.project.Project;
import org.apache.maven.continuum.model.project.ProjectGroup;
import org.apache.maven.continuum.web.exception.AuthorizationRequiredException;
import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 */
@Component( role = com.opensymphony.xwork2.Action.class, hint = "projectView", instantiationStrategy = "per-lookup"  )
public class ProjectViewAction
    extends ContinuumActionSupport
{
    private static final Logger logger = LoggerFactory.getLogger( ProjectViewAction.class );

    private Project project;

    private int projectId;

    private String lastBuildDateTime;

    public String execute()
        throws ContinuumException
    {
        ProjectGroup projectGroup = getProjectGroup();

        try
        {
            checkViewProjectGroupAuthorization( projectGroup.getName() );
        }
        catch ( AuthorizationRequiredException e )
        {
            return REQUIRES_AUTHORIZATION;
        }

        project = getContinuum().getProjectWithAllDetails( projectId );
        if ( project.getLatestBuildId() > 0 )
        {
            try
            {
                BuildResult lastBuildResult = getContinuum().getBuildResult( project.getLatestBuildId() );
                if ( lastBuildResult != null )
                {
                    this.setLastBuildDateTime( dateFormatter.format( new Date( lastBuildResult.getEndTime() ) ) );
                }
            }
            catch ( ContinuumException e )
            {
                logger.info( "buildResult with id " + project.getLatestBuildId() + " has been deleted" );
            }
        }

        return SUCCESS;
    }

    public void setProjectId( int projectId )
    {
        this.projectId = projectId;
    }

    public Project getProject()
    {
        return project;
    }

    public int getProjectId()
    {
        return projectId;
    }

    /**
     * Returns the {@link ProjectGroup} instance obtained for
     * the specified project group Id, or null if it were not set.
     *
     * @return the projectGroup
     */
    public ProjectGroup getProjectGroup()
        throws ContinuumException
    {
        return getContinuum().getProjectGroupByProjectId( projectId );
    }

    public String getLastBuildDateTime()
    {
        return lastBuildDateTime;
    }

    public void setLastBuildDateTime( String lastBuildDateTime )
    {
        this.lastBuildDateTime = lastBuildDateTime;
    }
}
