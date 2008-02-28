<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="c1" uri="continuum" %>
<html>
  <ww:i18n name="localization.Continuum">
    <head>
        <title><ww:text name="surefireReport.page.title"/></title>
    </head>
    <body>
      <div id="h3">
        <div>
          <p style="border-top: 1px solid transparent; border-bottom: 1px solid #DFDEDE;">
            <a style="border: 1px solid #DFDEDE; padding-left: 1em; padding-right: 1em; text-decoration: none;" href="<ww:url value="/projectView.action?projectId="/><ww:property value="projectId"/>&projectName=<ww:property value="projectName"/>"><ww:text name="info"/></a>
            <a style="border: 1px solid #DFDEDE; padding-left: 1em; padding-right: 1em; text-decoration: none;" href="<ww:url value="/buildResults.action?projectId="/><ww:property value="projectId"/>&projectName=<ww:property value="projectName"/>"><ww:text name="builds"/></a>
            <a style="border: 1px solid #DFDEDE; padding-left: 1em; padding-right: 1em; text-decoration: none;" href="<ww:url value="/workingCopy.action?projectId="/><ww:property value="projectId"/>&projectName=<ww:property value="projectName"/>"><ww:text name="workingCopy"/></a>
          </p>
        </div>

        <h3>
            <ww:text name="surefireReport.section.title">
              <ww:param><ww:property value="projectName"/></ww:param>
              <ww:param><ww:property value="buildId"/></ww:param>
            </ww:text>
        </h3>

        <h4><ww:text name="surefireReport.summary"/></h4>
        <ec:table items="testSummaryList"
                  var="summary"
                  showExports="false"
                  showPagination="false"
                  showStatusBar="false"
                  sortable="false"
                  filterable="false">
          <ec:row>
            <ec:column property="tests" title="surefireReport.tests"/>
            <ec:column property="errors" title="surefireReport.errors"/>
            <ec:column property="failures" title="surefireReport.failures"/>
            <ec:column property="successRate" title="surefireReport.successRate"/>
            <ec:column property="elapsedTime" title="surefireReport.time"/>
          </ec:row>
        </ec:table>

        <h4><ww:text name="surefireReport.packageList"/></h4>
        <ec:table items="testPackageList"
                  var="report"
                  showExports="false"
                  showPagination="false"
                  showStatusBar="false"
                  sortable="false"
                  filterable="false">
          <ec:row>
            <ec:column property="name" title="surefireReport.package">
              <a href="#<c:out value="${pageScope.report.name}"/>"><c:out value="${pageScope.report.name}"/></a>
            </ec:column>
            <ec:column property="tests" title="surefireReport.tests"/>
            <ec:column property="errors" title="surefireReport.errors"/>
            <ec:column property="failures" title="surefireReport.failures"/>
            <ec:column property="successRate" title="surefireReport.successRate"/>
            <ec:column property="elapsedTime" title="surefireReport.time"/>
          </ec:row>
        </ec:table>

        <ww:iterator value="testPackageList">
          <h5><a name="<ww:property value="name"/>"><ww:property value="name"/></a></h5>
          <ec:table items="children"
                    var="report"
                    showExports="false"
                    showPagination="false"
                    showStatusBar="false"
                    sortable="false"
                    filterable="false">
            <ec:row>
              <!-- @todo there must be a better option than to use #attr -->
              <ww:if test="#attr.report.errors > 0 || #attr.report.failures > 0">
                <ec:column property="icon" title="&nbsp;">
                  <img src="<ww:url value="/images/icon_error_sml.gif"/>" alt="<ww:text name="message.error"/>" title="<ww:text name="message.error"/>"/>
                </ec:column>
              </ww:if>
              <ww:else>
                <ec:column property="icon" title="&nbsp;">
                  <img src="<ww:url value="/images/icon_success_sml.gif"/>" alt="<ww:text name="message.success"/>" title="<ww:text name="message.success"/>"/>
                </ec:column>
              </ww:else>
              <ec:column property="name" title="surefireReport.class">
                <a href="#<c:out value="${pageScope.report.id}"/>"><c:out value="${pageScope.report.name}"/></a>
              </ec:column>
              <ec:column property="tests" title="surefireReport.tests"/>
              <ec:column property="errors" title="surefireReport.errors"/>
              <ec:column property="failures" title="surefireReport.failures"/>
              <ec:column property="successRate" title="surefireReport.successRate"/>
              <ec:column property="elapsedTime" title="surefireReport.time"/>
            </ec:row>
          </ec:table>
        </ww:iterator>

        <h4><ww:text name="surefireReport.testCases"/></h4>
        <ww:iterator value="testPackageList">
          <ww:iterator value="children">
            <h5><a name="<ww:property value="id"/>"><ww:property value="name"/></a></h5>
            <ec:table items="children"
                      var="testCase"
                      showExports="false"
                      showPagination="false"
                      showStatusBar="false"
                      sortable="false"
                      filterable="false">
              <ec:row>
                <!-- @todo there must be a better option than to use #attr -->
                <ww:if test="#attr.testCase.failureType != null">
                  <ec:column property="icon" title="&nbsp;">
                    <img src="<ww:url value="/images/icon_error_sml.gif"/>" alt="<ww:text name="message.error"/>" title="<ww:text name="message.error"/>"/>
                  </ec:column>
                  <ec:column property="name" title="surefireReport.testCase" sortable="false">
                    <c:out value="${pageScope.testCase.name}"/><br/><br/>
                    <c:out value="${pageScope.testCase.failureDetails}"/>
                  </ec:column>
                </ww:if>
                <ww:else>
                  <ec:column property="icon" title="&nbsp;">
                    <img src="<ww:url value="/images/icon_success_sml.gif"/>" alt="<ww:text name="message.success"/>" title="<ww:text name="message.success"/>"/>
                  </ec:column>
                  <ec:column property="name" title="surefireReport.testCase"/>
                </ww:else>
                <ec:column property="time" title="surefireReport.time"/>
              </ec:row>
            </ec:table>
          </ww:iterator>
        </ww:iterator>
      </div>
    </body>
  </ww:i18n>
</html>