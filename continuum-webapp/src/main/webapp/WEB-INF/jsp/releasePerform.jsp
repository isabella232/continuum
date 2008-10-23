<%--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
  <s:i18n name="localization.Continuum">
    <head>
        <title><s:text name="releaseProject.page.title"/></title>
    </head>
    <body>
      <h2><s:text name="releasePerform.section.title"/></h2>
      <s:form action="releasePerform" method="post" validate="true">
        <h3><s:text name="releasePerform.parameters"/></h3>
        <s:hidden name="projectId"/>
        <s:hidden name="releaseId"/>
        <div class="axial">
          <table border="1" cellspacing="2" cellpadding="3" width="100%">
            <s:textfield label="Maven Arguments" name="goals" value="clean deploy"/>
            <s:checkbox label="Use Release Profile" name="useReleaseProfile" value="true"/>
          </table>
        </div>
        <s:submit/>
      </s:form>
    </body>
  </s:i18n>
</html>
