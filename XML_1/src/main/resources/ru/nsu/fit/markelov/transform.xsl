<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html"/>
	<xsl:template match="/*[local-name()='people']">
		<html lang="en"/>
		<head>
			<title>Relatives</title>
			<meta charset="utf-8"/>
			<link rel="stylesheet" href="css/reset.css"/>
			<link rel="stylesheet" href="css/output.css"/>
		</head>
		<html>
			<body>
				<xsl:apply-templates select="person"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="person">
		<xsl:if test="(parents/father) and (parents/mother) and ((id(parents/father/@id)/parents) or (id(parents/mother/@id)/parents)) and ((brothers) or (sisters))">
			<table>
				<tr>
					<th>Relative</th>
					<th>Name</th>
					<th>Gender</th>
					<th>Father</th>
					<th>Mother</th>
					<th>Brother</th>
					<th>Sister</th>
					<th>Son</th>
					<th>Daughter</th>
					<th>Grandfather</th>
					<th>Grandmother</th>
					<th>Uncle</th>
					<th>Aunt</th>
				</tr>
				<tr>
					<td>(Self)</td>
					<xsl:call-template name="family">
						<xsl:with-param name="x" select="."/>
					</xsl:call-template>
				</tr>
				<tr>
					<td>Father</td>
					<xsl:call-template name="family">
						<xsl:with-param name="x" select="id(parents/father/@id)"/>
					</xsl:call-template>
				</tr>
				<tr>
					<td>Mother</td>
					<xsl:call-template name="family">
						<xsl:with-param name="x" select="id(parents/mother/@id)"/>
					</xsl:call-template>
				</tr>
				<xsl:for-each select="id(brothers/brother/@id)">
					<tr>
						<td>Brother</td>
						<xsl:call-template name="family">
							<xsl:with-param name="x" select="."/>
						</xsl:call-template>
					</tr>
				</xsl:for-each>
				<xsl:for-each select="id(sisters/sister/@id)">
					<tr>
						<td>Sister</td>
						<xsl:call-template name="family">
							<xsl:with-param name="x" select="."/>
						</xsl:call-template>
					</tr>
				</xsl:for-each>
			</table>
			<br/>
		</xsl:if>
	</xsl:template>

	<xsl:template name="family">
		<xsl:param name="x"/>
		<td><xsl:value-of select="$x/@name"/></td>
		<td><xsl:value-of select="$x/@gender"/></td>
		<td><xsl:value-of select="id($x/parents/father/@id)/@name"/></td>
		<td><xsl:value-of select="id($x/parents/mother/@id)/@name"/></td>
		<td>
			<xsl:for-each select="id($x/brothers/brother/@id)">
				<xsl:value-of select="@name"/><p/>
			</xsl:for-each>
		</td>
		<td>
			<xsl:for-each select="id($x/sisters/sister/@id)">
				<xsl:value-of select="@name"/><p/>
			</xsl:for-each>
		</td>
		<td>
			<xsl:for-each select="id($x/sons/son/@id)">
				<xsl:value-of select="@name"/><p/>
			</xsl:for-each>
		</td>
		<td>
			<xsl:for-each select="id($x/daughters/daughter/@id)">
				<xsl:value-of select="@name"/><p/>
			</xsl:for-each>
		</td>
		<td>
			<xsl:if test="id($x/parents/father/@id)/parents/father">
				<xsl:value-of select="id(id($x/parents/father/@id)/parents/father/@id)/@name"/>
				<p/>
			</xsl:if>
			<xsl:if test="id($x/parents/mother/@id)/parents/father">
				<xsl:value-of select="id(id($x/parents/mother/@id)/parents/father/@id)/@name"/>
			</xsl:if>
		</td>
		<td>
			<xsl:if test="id($x/parents/father/@id)/parents/mother">
				<xsl:value-of select="id(id($x/parents/father/@id)/parents/mother/@id)/@name"/>
				<p/>
			</xsl:if>
			<xsl:if test="id($x/parents/mother/@id)/parents/mother">
				<xsl:value-of select="id(id($x/parents/mother/@id)/parents/mother/@id)/@name"/>
			</xsl:if>
		</td>
		<td>
			<xsl:if test="id($x/parents/father/@id)/brothers">
				<xsl:value-of select="id(id($x/parents/father/@id)/brothers/brother/@id)/@name"/>
				<p/>
			</xsl:if>
			<xsl:if test="id($x/parents/mother/@id)/brothers">
				<xsl:value-of select="id(id($x/parents/mother/@id)/brothers/brother/@id)/@name"/>
			</xsl:if>
		</td>
		<td>
			<xsl:if test="id($x/parents/father/@id)/sisters">
				<xsl:value-of select="id(id($x/parents/father/@id)/sisters/sister/@id)/@name"/>
				<p/>
			</xsl:if>
			<xsl:if test="id($x/parents/mother/@id)/sister">
				<xsl:value-of select="id(id($x/parents/mother/@id)/sisters/sister/@id)/@name"/>
			</xsl:if>
		</td>
	</xsl:template>

</xsl:stylesheet>
