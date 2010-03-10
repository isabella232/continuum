package org.apache.continuum.web.test;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

//import org.apache.continuum.web.test.parent.AbstractBuildQueueTest;
import org.testng.annotations.Test;
import org.apache.continuum.web.test.parent.AbstractBuildAgentsTest;

@Test( groups = { "agent" }, dependsOnMethods = { "testWithCorrectUsernamePassword" } )
public class BuildAgentsTest
    extends AbstractBuildAgentsTest
{
    public void testAddBuildAgent()
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_DESCRIPTION = getProperty( "BUILD_AGENT_DESCRIPTION" );
        String BUILD_AGENT_NAME2 = getProperty( "BUILD_AGENT_NAME2" );
        String BUILD_AGENT_DESCRIPTION2 = getProperty( "BUILD_AGENT_DESCRIPTION2" );
        String BUILD_AGENT_NAME3 = getProperty( "BUILD_AGENT_NAME3" );
        String BUILD_AGENT_DESCRIPTION3 = getProperty( "BUILD_AGENT_DESCRIPTION3" );

        try
        {
            enableDistributedBuilds();
            goToAddBuildAgent();
            addBuildAgent( BUILD_AGENT_NAME, BUILD_AGENT_DESCRIPTION, true, false );
            goToAddBuildAgent();
            addBuildAgent( BUILD_AGENT_NAME2, BUILD_AGENT_DESCRIPTION2, true, true );
            goToAddBuildAgent();
            addBuildAgent( BUILD_AGENT_NAME3, BUILD_AGENT_DESCRIPTION3, true, false );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testEditBuildAgent" } )
    public void testAddAnExistingBuildAgent()
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_DESCRIPTION = getProperty( "BUILD_AGENT_DESCRIPTION" );

        try
        {
            enableDistributedBuilds();
            goToAddBuildAgent();
            addBuildAgent( BUILD_AGENT_NAME, BUILD_AGENT_DESCRIPTION, false, false ) ;
            assertTextPresent( "Build agent already exists" );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testAddBuildAgent" } )
    public void testEditBuildAgent()
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_DESCRIPTION = getProperty( "BUILD_AGENT_DESCRIPTION" );
        String new_agentDescription = "new_agentDescription";

        try
        {
            enableDistributedBuilds();
            goToEditBuildAgent( BUILD_AGENT_NAME, BUILD_AGENT_DESCRIPTION );
            addEditBuildAgent( BUILD_AGENT_NAME, new_agentDescription );
            goToEditBuildAgent( BUILD_AGENT_NAME, new_agentDescription);
            addEditBuildAgent( BUILD_AGENT_NAME, BUILD_AGENT_DESCRIPTION );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testAddAnExistingBuildAgent" } )
    public void testDeleteBuildAgent()
    {
        try
        {
            enableDistributedBuilds();
            goToBuildAgentPage();
            String BUILD_AGENT_NAME3 = getProperty( "BUILD_AGENT_NAME3" );
            removeBuildAgent( BUILD_AGENT_NAME3 );
            assertTextNotPresent( BUILD_AGENT_NAME3 );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

	@Test( dependsOnMethods = { "testDeleteBuildAgent" } )
    public void testAddEmptyBuildAgent()
    {
    	String BUILD_AGENT_DESCRIPTION = getProperty( "BUILD_AGENT_DESCRIPTION" );

    	try
    	{
    	    enableDistributedBuilds();
    	    goToAddBuildAgent();
    	    addBuildAgent( "", BUILD_AGENT_DESCRIPTION, false, false ) ;
    	    assertTextPresent( "Build agent url is required." );
    	}
    	finally
    	{
    	    disableDistributedBuilds();
    	}
    }

//TESTS FOR BUILD AGENT GROUPS

    @Test( dependsOnMethods = { "testAddBuildAgent", "testDeleteBuildAgent" } )
    public void testAddBuildAgentGroup()
        throws Exception
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_NAME2 = getProperty( "BUILD_AGENT_NAME2" );
        String BUILD_AGENT_GROUPNAME = getProperty( "BUILD_AGENT_GROUPNAME" );

        try
        {
            enableDistributedBuilds();
            goToAddBuildAgentGroup();
            addEditBuildAgentGroup( BUILD_AGENT_GROUPNAME, new String[] { BUILD_AGENT_NAME, BUILD_AGENT_NAME2 }, new String[] {}, true );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testAddBuildAgentGroup" } )
    public void testEditBuildAgentGroup()
        throws Exception
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_NAME2 = getProperty( "BUILD_AGENT_NAME2" );
        String BUILD_AGENT_GROUPNAME = getProperty( "BUILD_AGENT_GROUPNAME" );

        String newName = "new_agentgroupname";
        try
        {
            enableDistributedBuilds();
            goToEditBuildAgentGroup( BUILD_AGENT_GROUPNAME, new String[] { BUILD_AGENT_NAME, BUILD_AGENT_NAME2 } );
            addEditBuildAgentGroup( newName, new String[] {},
                             new String[] { BUILD_AGENT_NAME2 }, true );
            goToEditBuildAgentGroup( newName, new String[] { BUILD_AGENT_NAME } );
            addEditBuildAgentGroup( BUILD_AGENT_GROUPNAME, new String[] { BUILD_AGENT_NAME2 },
                             new String[] {}, true );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testEditBuildAgentGroup" } )
    public void testAddAnExistingBuildAgentGroup()
        throws Exception
    {
        String BUILD_AGENT_NAME = getProperty( "BUILD_AGENT_NAME" );
        String BUILD_AGENT_NAME2 = getProperty( "BUILD_AGENT_NAME2" );
        String BUILD_AGENT_GROUPNAME = getProperty( "BUILD_AGENT_GROUPNAME" );

        try
        {
            enableDistributedBuilds();
            goToAddBuildAgentGroup();
           	addEditBuildAgentGroup( BUILD_AGENT_GROUPNAME, new String[] { BUILD_AGENT_NAME, BUILD_AGENT_NAME2 }, new String[] {}, false );
           	assertTextPresent( "Build agent group already exists." );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testAddAnExistingBuildAgentGroup" } )
    public void testAddEmptyBuildAgentGroupName()
        throws Exception
    {
        try
        {
            enableDistributedBuilds();
            goToAddBuildAgentGroup();
            addEditBuildAgentGroup( "", new String[] {}, new String[] {}, false );
            assertTextPresent( "Build agent group name required." );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }

    @Test( dependsOnMethods = { "testAddEmptyBuildAgentGroupName" } )
    public void testDeleteBuildAgentGroup()
    {
        String BUILD_AGENT_GROUPNAME = getProperty( "BUILD_AGENT_GROUPNAME" );

        try
        {
            enableDistributedBuilds();
            removeBuildAgentGroup( BUILD_AGENT_GROUPNAME );
        }
        finally
        {
            disableDistributedBuilds();
        }
    }
}
