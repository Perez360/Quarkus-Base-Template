package com.codex.base.utils

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.JAXBException
import jakarta.xml.bind.Marshaller
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.StringReader

class XMLUtils {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun <T> fromXml(type: Class<T>, xml: String): T? {
        //TODO
        return try {
            val jaxbContext = JAXBContext.newInstance(type)
                .createUnmarshaller()

            jaxbContext.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            jaxbContext.unmarshal(StringReader(xml)) as T
        } catch (jaxbEx: JAXBException) {
            logger.info("Failed to convert xml to ${type.simpleName}: ${jaxbEx.message}")
            null
        }
    }

    inline fun <reified S> toXml(source: S) {
        try {
            val jaxbContext = JAXBContext.newInstance(S::class.java)
                .createMarshaller()

            jaxbContext.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            jaxbContext.marshal(source, System.out) //TODO
        } catch (jaxbEx: JAXBException) {
            logger.info("Failed to convert from ${S::class.simpleName} to xml: ${jaxbEx.message}")
        }
    }
}