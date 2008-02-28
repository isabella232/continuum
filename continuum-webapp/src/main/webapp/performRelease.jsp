<%@ taglib uri="/webwork" prefix="ww" %>
<html>
  <ww:i18n name="localization.Continuum">
    <head>
        <title><ww:text name="releaseProject.page.title"/></title>
    </head>
    <body>
      <h2>Perform Project Release</h2>
      <ww:form action="performRelease!doPerform.action" method="post">
        <h3>Common Release Parameters</h3>
        <ww:hidden name="projectId"/>
        <ww:hidden name="releaseId"/>
        <div class="axial">
          <table border="1" cellspacing="2" cellpadding="3" width="100%">
            <ww:if test="releaseId.equals(\"\")">
              <ww:textfield label="SCM Connection URL" name="scmUrl" required="true"/>
              <ww:textfield label="SCM Username" name="scmUsername"/>
              <ww:textfield label="SCM Password" name="scmPassword"/>
              <ww:textfield label="SCM Tag" name="scmTag" required="true"/>
              <ww:textfield label="SCM Tag Base" name="scmTagBase" required="true"/>
            </ww:if>
            <ww:textfield label="Maven Arguments" name="goals"/>
            <ww:checkbox label="Use Release Profile" name="useReleaseProfile" value="true"/>
          </table>
        </div>
        <ww:submit/>
      </ww:form>
    </body>
  </ww:i18n>
</html>