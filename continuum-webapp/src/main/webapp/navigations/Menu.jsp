<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>

<ww:i18n name="localization.Continuum">
<div id="navcolum">
  <div id="projectmenu" class="toolgroup">
    <div class="label">Continuum</div>
    <div>
      <div class="body">
        <a href="<ww:url value="/about.jsp" includeParams="none"/>"><ww:text name="menu.continuum.about"/></a>
      </div>
      <div class="body">
        <a href="<ww:url value="/groupSummary.action" includeParams="none"/>"><ww:text name="menu.continuum.showProjectGroups"/></a>
      </div>
    </div>
  </div>

  <authz:authorize ifAnyGranted="ROLE_admin,ROLE_addProject">
    <div id="projectmenu" class="toolgroup">
      <div class="label"><ww:text name="menu.addProject"/></div>
      <div>
        <div class="body">
          <a href="<ww:url value="/addMavenTwoProject!default.action" includeParams="none"/>"><ww:text name="menu.add.m2Project"/></a>
        </div>
        <div class="body">
          <a href="<ww:url value="/addMavenOneProject!default.action" includeParams="none"/>"><ww:text name="menu.add.m1Project"/></a>
        </div>
        <div class="body">
          <a href="<ww:url value="/addProject!default.action" includeParams="none"><ww:param name="projectType">ant</ww:param></ww:url>"><ww:text name="menu.add.antProject"/></a>
        </div>
        <div class="body">
          <a href="<ww:url value="/addProject!default.action" includeParams="none"><ww:param name="projectType">shell</ww:param></ww:url>"><ww:text name="menu.add.shellProject"/></a>
        </div>
      </div>
    </div>
  </authz:authorize>      

  <authz:authorize ifAnyGranted="ROLE_admin,ROLE_manageUsers,ROLE_manageSchedule,ROLE_manageConfiguration">
    <div id="projectmenu" class="toolgroup">
      <div class="label"><ww:text name="menu.administration"/></div>
      <div>
        <authz:authorize ifAnyGranted="ROLE_admin,ROLE_manageSchedule">
          <div class="body">
            <a href="<ww:url value="/schedules.action" includeParams="none"/>"><ww:text name="menu.administration.schedules"/></a>
          </div>
        </authz:authorize>      
        <authz:authorize ifAnyGranted="ROLE_admin,ROLE_manageConfiguration">
          <div class="body">
            <a href="<ww:url value="/configuration!default.action" includeParams="none"/>"><ww:text name="menu.administration.configuration"/></a>
          </div>
        </authz:authorize>      
        <authz:authorize ifAnyGranted="ROLE_admin,ROLE_manageUsers">
          <div class="body">
            <a href="<ww:url value="/user/list.action" includeParams="none"/>"><ww:text name="menu.administration.users"/></a>
          </div>
          <div class="body">
            <a href="<ww:url value="/user/group/list.action" includeParams="none"/>"><ww:text name="menu.administration.userGroups"/></a>
          </div>
        </authz:authorize>
      </div>
    </div>
  </authz:authorize>      

  <authz:authorize ifNotGranted="ROLE_ANONYMOUS">
    <div id="projectmenu" class="toolgroup">
      <div class="label"><ww:text name="menu.account.options"/></div>
      <div>
        <div class="body">
          <a href="<ww:url value='/user/myAccount!editMe.action' includeParams='none'/>"><ww:text name="user.edit.account"/></a>
        </div>
      </div>
    </div>
  </authz:authorize>

  <div id="projectmenu" class="toolgroup">
    <div class="label">Legend</div>
    <div id="legend">
      <div id="litem1" class="body">Build Now</div>
      <div id="litem2" class="body">Build History</div>
      <div id="litem3" class="body">Build In Progess</div>
      <div id="litem4" class="body">Checking Out Build</div>
      <div id="litem5" class="body">Queued Build</div>
      <div id="litem6" class="body">Delete</div>
      <div id="litem7" class="body">Edit</div>
      <div id="litem8" class="body">Build in Success</div>
      <div id="litem9" class="body">Build in Failure</div>
      <div id="litem10" class="body">Build in Error</div>
    </div>
  </div>
</div>
</ww:i18n>