<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">CGH Copy Number</div>


<html:radio property="copyNumber" value="amplified"/> Amplified
				<html:text property="cnAmplified"/></br>

<html:radio property="copyNumber" value="deleted"/> Deleted
				<html:text property="cnDeleted"/></br>
<html:radio property="copyNumber" value="ampdel"/> Amplified or Deleted &nbsp;
<blockquote>
Amplified&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <html:text property="cnADAmplified"/>
&nbsp;
<Br>
Deleted
<html:text property="cnADDeleted"/>
&nbsp;
</blockquote>
<html:radio property="copyNumber" value="unchange"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="cnUnchangeFrom"/>-to-
				<html:text property="cnUnchangeTo"/>

<html:errors property="cnADAmplified"/></br>
<html:errors property="cnADDeleted"/></br>
<html:errors property="cnAmplified"/></br>
<html:errors property="cnDeleted"/></br>
<html:errors property="cnUnchangeFrom"/></br>
<html:errors property="cnUnchangeTo"/></br>
<html:errors property="copyNumber"/></br>




