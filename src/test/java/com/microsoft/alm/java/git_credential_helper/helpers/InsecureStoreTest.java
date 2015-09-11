package com.microsoft.alm.java.git_credential_helper.helpers;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InsecureStoreTest
{
    @Test public void serialization_instanceToXmlToInstance()
    {
        final InsecureStore input = new InsecureStore();
        input.Tokens.put("alpha", null);
        input.Tokens.put("bravo", null);
        input.Credentials.put("charlie", null);

        final InsecureStore actual = clone(input);

        Assert.assertEquals(2, actual.Tokens.size());
        Assert.assertTrue(actual.Tokens.containsKey("alpha"));
        Assert.assertFalse(actual.Tokens.containsKey("charlie"));

        Assert.assertEquals(1, actual.Credentials.size());
        Assert.assertTrue(actual.Credentials.containsKey("charlie"));
    }

    static InsecureStore clone(InsecureStore inputStore)
    {
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try
        {
            baos = new ByteArrayOutputStream();
            final PrintStream ps = new PrintStream(baos);

            inputStore.toXml(ps);
            ps.flush();

            final String xmlString = baos.toString();

            bais = new ByteArrayInputStream(xmlString.getBytes());
            final InsecureStore result = InsecureStore.fromXml(bais);

            return result;
        }
        finally
        {
            IOUtils.closeQuietly(baos);
            IOUtils.closeQuietly(bais);
        }
    }
}
