<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>

<%-- Acegi configuration --%>
<c:set var="authentication" value="${sessionScope['ACEGI_SECURITY_CONTEXT'].authentication}"/>
<c:set var="user" value="${authentication.principal}" scope="session"/>

<ww:i18n name="localization.Continuum">
<div id="banner">
  <table border="1" cellpadding="8" cellspacing="0" width="100%">
    <tbody>
      <tr>
        <td>
          <a href="http://maven.apache.org/continuum">
            <img src="<ww:url value="/images/continuum_logo_75.gif"/>" alt="Continuum" title="Continuum" border="0">
          </a>
        </td>
        <td>
          <div align="right">
            <ww:action name="companyInfo!default" executeResult="true"/>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</div>

<div id="breadcrumbs">
  <div style="float: right;">
    <a href="http://maven.apache.org/continuum">Continuum</a> |
    <a href="http://maven.apache.org/">Maven</a> |
    <a href="http://www.apache.org/">Apache</a>
  </div>

  <div>
      Welcome,

      <authz:authorize ifNotGranted="ROLE_ANONYMOUS">
        <b><authz:authentication operation="username"/></b> -
        <a href="<c:url value='/user/logoff.jsp'/>">Logoff</a>
      </authz:authorize>

      <authz:authorize ifAllGranted="ROLE_ANONYMOUS">
        <a href="<ww:url value="/user/login.jsp"/>">Login</a>
      </authz:authorize>

  </div>
</div>
</ww:i18n>