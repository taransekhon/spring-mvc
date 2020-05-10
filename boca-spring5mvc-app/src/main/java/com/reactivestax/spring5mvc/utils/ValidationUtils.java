package com.reactivestax.spring5mvc.utils;

import com.reactivestax.spring5mvc.constants.CommonConstants;
import com.reactivestax.spring5mvc.exceptions.InvalidDataException;
import com.reactivestax.spring5mvc.utils.dto.ClientMetaData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * This class contains business related validation utility methods
 */
public class ValidationUtils {
    private ValidationUtils() {

    }

    //@formatter:off

    /**
     * validates the clientCardNumber String
     *
     * @param clientCardNumber
     */
    public static void validateClientCardNumber(String clientCardNumber) {
        if (StringUtils.isBlank(clientCardNumber)
                || (!NumberUtils.isDigits(clientCardNumber))
                || StringUtils.length(clientCardNumber) != 16
                ||
                !(StringUtils.startsWith(clientCardNumber, "451901")
                        || StringUtils.startsWith(clientCardNumber, "451902")
                        || StringUtils.startsWith(clientCardNumber, "451903"))
                ) {
            InvalidDataException invalidDataException = new InvalidDataException();
            invalidDataException.getErrorMap().put(CommonConstants.CLIENT_CARD_NUMBER, CommonConstants.CLIENT_CARD_NUMBER + " has to be valid 16 digit number or tokenized card number");
            throw invalidDataException;
        }
    }
    //@formatter:on

    /**
     * validates the channelIdentifier part in clientMetaData request header.
     *
     * @param clientMetaData
     */
    public static void validateChannelIdentifierInClientMetaData(ClientMetaData clientMetaData) {
        if (StringUtils.isBlank(clientMetaData.getChannelIdentifier())) {
            InvalidDataException inValidDataException = new InvalidDataException();
            inValidDataException.getErrorMap().put("client-metadata.channelIdentifier", "channelIdentifier is missing in client-metadata");
            throw inValidDataException;
        }
    }

}
