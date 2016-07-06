package org.solrmarc.solr;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.solr.common.util.NamedList;

public class StdOutProxy extends SolrProxy
{
    PrintStream output;
    
    public StdOutProxy(PrintStream out)
    {
        this.output = out;
    }
    
    public int addDoc(SolrInputDocument inputDoc)
    {
        ArrayList<String> fNames = new ArrayList<String>();
        fNames.addAll(inputDoc.getFieldNames());
        Collections.sort(fNames);
        String id = inputDoc.getFieldValue("id").toString();
        for (String fieldName : fNames)
        {
            for (Object val : inputDoc.getFieldValues(fieldName))
            {
                output.print(id + " : " + fieldName + " = " + val.toString() + "\n");
            }
        }
        return(1);
    }
    
    @Override
    public int addDocs(Collection<SolrInputDocument> docQ)
    {
        int num = 0;
        for (SolrInputDocument doc : docQ)
        {
            num += this.addDoc(doc);
        }
        return(num);
    }

    public void close()
    {
        output.flush();
    }

    public SolrServer getSolrServer()
    {
        return(null);
    }
    
    public void commit(boolean optimize) throws IOException
    {
        output.flush();
    }

    public void delete(String id, boolean fromCommitted, boolean fromPending) throws IOException
    {
    }

    public void deleteAllDocs() throws IOException
    {
    }
}