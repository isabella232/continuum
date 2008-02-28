<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="continuum" prefix="c1" %>
<html>
  <ww:i18n name="localization.Continuum">
    <head>
        <title>
            <ww:text name="notifier.page.title">
                <ww:param>MSN</ww:param>
            </ww:text>
        </title>
    </head>
    <body>
      <div id="axial" class="h3">
        <h3>
            <ww:text name="notifier.section.title">
                <ww:param>MSN</ww:param>
            </ww:text>
        </h3>

        <div class="axial">
          <ww:form action="jabberNotifierSave.action" method="post">
            <ww:hidden name="notifierId"/>
            <ww:hidden name="projectId"/>
            <ww:hidden name="notifierType"/>
            <table>
              <tbody>
                <ww:textfield label="%{getText('notifier.msn.login.label')}" name="login" required="true"/>
                <ww:password label="%{getText('notifier.msn.password.label')}" name="password" required="true"/>
                <ww:textfield label="%{getText('notifier.msn.address.label')}" name="address" required="true"/>
                <ww:checkbox label="%{getText('notifier.event.sendOnSuccess')}" name="sendOnSuccess" value="sendOnSuccess" fieldValue="true"/>
                <ww:checkbox label="%{getText('notifier.event.sendOnFailure')}" name="sendOnFailure" value="sendOnFailure" fieldValue="true"/>
                <ww:checkbox label="%{getText('notifier.event.sendOnError')}" name="sendOnError" value="sendOnError" fieldValue="true"/>
                <ww:checkbox label="%{getText('notifier.event.sendOnWarning')}" name="sendOnWarning" value="sendOnWarning" fieldValue="true"/>
              </tbody>
            </table>
            <div class="functnbar3">
              <c1:submitcancel value="%{getText('save')}" cancel="%{getText('cancel')}"/>
            </div>
          </ww:form>
        </div>
      </div>
    </body>
  </ww:i18n>
</html>