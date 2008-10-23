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
<%@ taglib uri="continuum" prefix="c1" %>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<s:i18n name="localization.Continuum">
  <head>
    <title>
      <s:text name="profile.page.title"/>
    </title>
  </head>

  <body>
    <div id="axial" class="h3">
      <h3>
        <s:text name="profile.section.title"/>
      </h3>

      <div class="axial">
        <s:if test="hasActionErrors()">
          <h3>Action Error</h3>
        </s:if>
        <p>
          <s:actionerror/>
        </p>      
      </div>
      <table>
        <tr>
          <td>
          <s:form action="saveBuildEnv!save" method="post">

            <div class="axial">
              <!--  if other fields are added ProfileAction#save must be changed  -->
              <table>
                <tbody>
                  <s:hidden name="profile.id" />
                  <s:textfield label="%{getText('profile.name.label')}" name="profile.name"
                                required="true" />
                </tbody>
              </table>
              <div class="functnbar3">
                <c1:submitcancel value="%{getText('save')}" cancel="%{getText('cancel')}"/>
              </div>

            </div>
          </s:form>
          </td>
        </tr>
        <s:if test="profile.id != 0">
          <tr>
            <td>
              <div class="axial">
                <table width="100%">
                  <tbody>
                    <tr>
                      <td>
                        <ec:table items="profileInstallations"
                                  var="profileInstallation"
                                  showExports="false"
                                  showPagination="false"
                                  showStatusBar="false"
                                  sortable="false"
                                  filterable="false"
                                  width="100%"
                                  autoIncludeParameters="false">
                          <ec:row highlightRow="true">
                            <ec:column property="nameEdit" title="Installation Name" style="white-space: nowrap" width="50%">
                              <a href="editInstallation!edit.action?installation.installationId=<c:out value="${profileInstallation.installationId}"/>">
                                <c:out value="${profileInstallation.name}"/>
                              </a>
                               (<c:out value="${profileInstallation.varValue}"/>)
                            </ec:column>
                            <ec:column property="type" title="Type" style="white-space: nowrap" width="49%"/>
                            <ec:column property="id" title="&nbsp;" width="1%">
                              <a href="removeBuildEnvInstallation!removeInstallation.action?profile.id=<c:out value="${profile.id}"/>&installationId=<c:out value="${profileInstallation.installationId}"/>">
                                <img src="<s:url value='/images/delete.gif' includeParams="none"/>" alt="<s:text name='delete'/>" title="<s:text name='delete'/>" border="0" />
                              </a>                    
                            </ec:column>        
                          </ec:row>
                        </ec:table>                
                      </td>
                    </tr>
                  </tbody>
                </table>
                <s:if test="${!empty allInstallations}">
                  <s:form action="addInstallationBuildEnv!addInstallation.action" method="get">
                    <s:hidden name="profile.id" />
                    <div class="functnbar3">
                      <!-- can't use default profile to display this select -->
                      <s:select theme="profile" name="installationId" list="allInstallations" listKey="installationId" listValue="name" />
                      <s:submit value="%{getText('add')}"/>
                    </div>
                  </s:form>
                </s:if>
                <s:else>
                  <div class="warningmessage" style="color: red"><s:text name="profile.no.installations" /></div>
                </s:else>
              </div>              
            </td>
          </tr>
        </s:if>
        <s:else>
          <tr>
            <td>
              <s:if test="${empty allInstallations}">
                <div class="warningmessage" style="color: red"><s:text name="profile.no.installations" /></div>
              </s:if>
            </td>
          </tr> 
        </s:else>
      </table>
    </div>
  </body>
</s:i18n>
</html>
