package com.reactivestax.spring5mvc.utils.dto;

import javax.validation.constraints.NotBlank;

/**
 * The mandatory access-logs metadata to be sent by all calling clients.
 *
 */
public class ClientMetaData {

	@NotBlank(message = "appCode in client_metadata request header is mandatory")
	private String appCode;

	@NotBlank(message = "appOrg in client_metadata request header is mandatory")
	private String appOrg;

	@NotBlank(message = "appVersion in client_metadata request header is mandatory")
	private String appVersion;

	@NotBlank(message = "assetId in client_metadata request header is mandatory")
	private String assetId;

	private String channelIdentifier;
	private String id;

	private String ipAddress;

	private String language;

	@NotBlank(message = "legacyId in client_metadata request header is mandatory")
	private String legacyId;
	//
	@NotBlank(message = "physicalLocationId in client_metadata request header is mandatory")
	private String physicalLocationId ;

	//
	@NotBlank(message = "requestUniqueId in client_metadata request header is mandatory")
	private String requestUniqueId;

	public String getAppCode() {
		return appCode;
	}

	public String getAppOrg() {
		return appOrg;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAssetId() {
		return assetId;
	}

	public String getChannelIdentifier() {
		return channelIdentifier;
	}

	public String getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getLanguage() {
		return language;
	}

	public String getLegacyId() {
		return legacyId;
	}

	public String getPhysicalLocationId() {
		return physicalLocationId;
	}

	public String getRequestUniqueId() {
		return requestUniqueId;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public void setAppOrg(String appOrg) {
		this.appOrg = appOrg;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public void setChannelIdentifier(String channelIdentifier) {
		this.channelIdentifier = channelIdentifier;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLegacyId(String legacyId) {
		this.legacyId = legacyId;
	}

	public void setPhysicalLocationId(String physicalLocationId) {
		this.physicalLocationId = physicalLocationId;
	}

	public void setRequestUniqueId(String requestUniqueId) {
		this.requestUniqueId = requestUniqueId;
	}

	@Override
	public String toString() {
		return "ClientMetaData [requestUniqueId=" + requestUniqueId + ", id=" + id + ", appOrg=" + appOrg + ", appCode="
				+ appCode + ", appVersion=" + appVersion + ", physicalLocationId=" + physicalLocationId + ", assetId="
				+ assetId + ", legacyId=" + legacyId + ", language=" + language + ", ipAddress=" + ipAddress
				+ ", channelIdentifier=" + channelIdentifier + "]";
	}

}
