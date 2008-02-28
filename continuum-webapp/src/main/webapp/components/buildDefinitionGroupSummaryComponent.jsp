<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="continuum" prefix="c1" %>
<ww:i18n name="localization.Continuum">

  <h3>Group Build Definitions</h3>
  <ec:table items="groupBuildDefinitionSummaries"
            var="buildDefinitionSummary"
            showExports="false"
            showPagination="false"
            showStatusBar="false"
            filterable="false"
            sortable="false">
    <ec:row>
      <ec:column property="goals" title="projectView.buildDefinition.goals"/>
      <ec:column property="arguments" title="projectView.buildDefinition.arguments"/>
      <ec:column property="buildFile" title="projectView.buildDefinition.buildFile"/>
      <ec:column property="profile" title="projectView.buildDefinition.profile"/>
      <ec:column property="scheduleName" title="schedule"/>
      <ec:column property="from" title="projectView.buildDefinition.from"/>
      <ec:column property="isDefault" title="projectView.buildDefinition.default"/>
      <ec:column property="actions" title="&nbsp;">

        <c:if test="${projectGroup.permissions.write}">
          <ww:url id="editUrl" action="buildDefinition" method="input" namespace="/">
            <ww:param name="projectGroupId">${pageScope.buildDefinitionSummary.projectGroupId}</ww:param>
            <ww:param name="buildDefinitionId">${pageScope.buildDefinitionSummary.id}</ww:param>
          </ww:url>
          <ww:a href="%{editUrl}"><ww:text name="edit"/></ww:a>
        </c:if>
          &nbsp;
        <c:if test="${projectGroup.permissions.delete}">
          <ww:url id="removeUrl" action="removeGroupBuildDefinition" namespace="/">
            <ww:param name="projectGroupId">${pageScope.buildDefinitionSummary.projectGroupId}</ww:param>
            <ww:param name="buildDefinitionId">${pageScope.buildDefinitionSummary.id}</ww:param>
            <ww:param name="confirmed" value="false"/>
          </ww:url>
          <ww:a href="%{removeUrl}"><ww:text name="delete"/></ww:a>
        </c:if>

      </ec:column>
    </ec:row>
  </ec:table>

  <div class="functnbar3">
    <c:if test="${projectGroup.permissions.write}">
      <ww:form action="buildDefinition!input.action" method="post" namespace="/">
        <input type="hidden" name="projectGroupId" value="<ww:property value="${pageScope.buildDefinitionSummary.projectGroupId}"/>"/>
        <ww:submit value="%{getText('add')}"/>
      </ww:form>
    </c:if>
  </div>

  <h3>Project Build Definitions</h3>

  <ec:table items="projectBuildDefinitionSummaries"
            var="buildDefinitionSummary"
            showExports="false"
            showPagination="false"
            showStatusBar="false"
            filterable="false"
            sortable="false">
    <ec:row>
      <ec:column property="projectName" title="Project"/>
      <ec:column property="goals" title="projectView.buildDefinition.goals"/>
      <ec:column property="arguments" title="projectView.buildDefinition.arguments"/>
      <ec:column property="buildFile" title="projectView.buildDefinition.buildFile"/>
      <ec:column property="profile" title="projectView.buildDefinition.profile"/>
      <ec:column property="scheduleName" title="schedule"/>
      <ec:column property="from" title="projectView.buildDefinition.from"/>
      <ec:column property="isDefault" title="projectView.buildDefinition.default"/>
      <ec:column property="actions" title="&nbsp;">

        <c:if test="${projectGroup.permissions.write}">
          <ww:url id="editUrl" action="buildDefinition" method="input" namespace="/">
            <ww:param name="projectId">${pageScope.buildDefinitionSummary.projectId}</ww:param>
            <ww:param name="buildDefinitionId">${pageScope.buildDefinitionSummary.id}</ww:param>
          </ww:url>
          <ww:a href="%{editUrl}"><ww:text name="edit"/></ww:a>
        </c:if>
        &nbsp;
        <c:if test="${projectGroup.permissions.delete}">
          <ww:url id="removeUrl" action="removeProjectBuildDefinition" namespace="/">
            <ww:param name="projectId">${pageScope.buildDefinitionSummary.projectId}</ww:param>
            <ww:param name="buildDefinitionId">${pageScope.buildDefinitionSummary.id}</ww:param>
            <ww:param name="confirmed" value="false"/>
          </ww:url>
          <ww:a href="%{removeUrl}"><ww:text name="delete"/></ww:a>
        </c:if>

      </ec:column>
    </ec:row>
  </ec:table>
</ww:i18n>