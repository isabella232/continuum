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

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 *
 * @plexus.component
 *   role="com.opensymphony.xwork.Action"
 *   role-hint="buildProject"
 */
public class BuildProjectAction
    extends ContinuumActionSupport
{
    private int projectId;

    private int buildDefinitionId;

    public String execute()
        throws ContinuumException
    {
        if ( projectId > 0 )
        {
            if ( buildDefinitionId > 0 )
            {
                getContinuum().buildProjectWithBuildDefinition( projectId, buildDefinitionId );
            }
            else
            {
                getContinuum().buildProject( projectId );
            }
        }
        else
        {
            if ( buildDefinitionId > 0 )
            {
                getContinuum().buildProjectsWithBuildDefinition( buildDefinitionId );
            }
            else
            {
                getContinuum().buildProjects();
            }
        }

        return SUCCESS;
    }

    public void setProjectId( int projectId )
    {
        this.projectId = projectId;
    }

    public int getProjectId()
    {
        return projectId;
    }

    public void setBuildDefinitionId( int buildDefinitionId )
    {
        this.buildDefinitionId = buildDefinitionId;
    }

    public int getBuildDefinition()
    {
        return buildDefinitionId;
    }
}
