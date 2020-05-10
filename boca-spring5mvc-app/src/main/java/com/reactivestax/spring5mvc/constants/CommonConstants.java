package com.reactivestax.spring5mvc.constants;

/**
 * Contains constants which are used across all micro-services.
 *
 */
public class CommonConstants {
    public static final String CLIENT_METADATA_JSON_STR = "CLIENT_METADATA_JSON_STR";
    public static final String CLIENT_METADATA = "CLIENT_METADATA";
    public static final String CLIENT_METADATA_REQ_HEADER = "client-metadata";
    public static final String REQUESTED_RESOURCE = "REQUESTED_RESOURCE";
    public static final String CLIENT_CARD_NUMBER = "CLIENT_CARD_NUMBER";
    public static final String CCN = "CCN";
    public static final String TOKEN = "TOKEN";
    //
	public static final String ERR_MSG_RUNTIME = "Unexpected exception occured at runtime";
	public static final String ENV_PROD="prod";
	public static final String ENV_IST="ist";
	public static final String ENV_SDF="sdf";
	public static final String ENV_LOCAL="local";
	public static final String ENV_DEFAULT = "default";

	//ErrorMessages
    private CommonConstants(){
    	
    }
}
