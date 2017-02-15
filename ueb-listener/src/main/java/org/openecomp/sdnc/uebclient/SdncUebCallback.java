/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 *             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.sdnc.uebclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.openecomp.sdc.api.IDistributionClient;
import org.openecomp.sdc.api.consumer.IDistributionStatusMessage;
import org.openecomp.sdc.api.consumer.INotificationCallback;
import org.openecomp.sdc.api.notification.IArtifactInfo;
import org.openecomp.sdc.api.notification.INotificationData;
import org.openecomp.sdc.api.notification.IResourceInstance;
import org.openecomp.sdc.api.results.IDistributionClientDownloadResult;
import org.openecomp.sdc.api.results.IDistributionClientResult;
import org.openecomp.sdc.utils.ArtifactTypeEnum;
import org.openecomp.sdc.utils.DistributionActionResultEnum;
import org.openecomp.sdc.utils.DistributionStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SdncUebCallback implements INotificationCallback {

    private static final Logger LOG = LoggerFactory
            .getLogger(SdncUebCallback.class);

    private static final int NUM_PASSES = 2;

    private enum SdncArtifactType {
        VF_LICENSE_MODEL("vf-license-model", "vf-license-model-update",0),
        UNKNOWN("","",0);


        private String tag;
        private String rpcName;
        private int pass;

        public int getPass() {
            return pass;
        }

        private SdncArtifactType(String tag, String rpcName, int pass) {
            this.tag = tag;
            this.rpcName = rpcName;
            this.pass = pass;
        }

        public String getTag() {
            return tag;
        }

        public String getRpcUrl(String base) {
            return base+rpcName;
        }

        public static SdncArtifactType fromTag(String tag) {
            if (tag != null) {
                for (SdncArtifactType artifact: SdncArtifactType.values()) {
                    if (artifact.getTag().equalsIgnoreCase(tag)) {
                        return artifact;
                    }
                }
            }

            return UNKNOWN;
        }
    }

    private class SdncAuthenticator extends Authenticator {

        private final String user;
        private final String passwd;

        SdncAuthenticator(String user, String passwd) {
            this.user = user;
            this.passwd = passwd;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, passwd.toCharArray());
        }

    }

    private class DeployableArtifact {
        SdncArtifactType type;
        IArtifactInfo artifactInfo;
        String svcName;
        String resourceName;
        String artifactName;
        String artifactVersion;
        File file;

        public String getArtifactName() {
            return artifactName;
        }



        public String getArtifactVersion() {
            return artifactVersion;
        }


        public SdncArtifactType getType() {
            return type;
        }



        public IArtifactInfo getArtifactInfo() {
            return artifactInfo;
        }


        public File getFile() {
            return file;
        }




        public DeployableArtifact(SdncArtifactType type, String svcName, String resourceName, IArtifactInfo artifactInfo, File file) {
            this.type = type;
            this.artifactInfo = artifactInfo;
            this.artifactName = artifactInfo.getArtifactName();
            this.artifactVersion = artifactInfo.getArtifactVersion();
            this.file = file;
        }


        public DeployableArtifact(SdncArtifactType type, String svcName, String resourceName, String artifactName, String artifactVersion, File file) {
            this.type = type;
            this.artifactInfo = null;
            this.artifactName = artifactName;
            this.artifactVersion = artifactVersion;
            this.file = file;
        }



        public String getSvcName() {
            return svcName;
        }



        public String getResourceName() {
            return resourceName;
        }

    }

    private final IDistributionClient client;
    private final SdncUebConfiguration config;

    private final LinkedList<DeployableArtifact> deployList[];


    public SdncUebCallback(IDistributionClient client, SdncUebConfiguration config) {
        this.client = client;
        this.config = config;
        this.deployList = new LinkedList[NUM_PASSES];

        for (int i = 0 ; i < NUM_PASSES ; i++) {
            this.deployList[i] = new LinkedList<>();
        }
    }

    @Override
	public void activateCallback(INotificationData data) {

        LOG.info("Received notification : ("+data.getDistributionID()+","+data.getServiceName()+","+data.getServiceVersion()+
                ","+data.getServiceDescription());

        String incomingDirName = config.getIncomingDir();
        String archiveDirName = config.getArchiveDir();

        File incomingDir = null;
        File archiveDir = null;

        if (!incomingDir.exists()) {
            incomingDir.mkdirs();
        }


        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }

        // Process service level artifacts
        List<IArtifactInfo> artifactList = data.getServiceArtifacts();

        if (artifactList != null) {

            incomingDir = new File(incomingDirName + "/" + escapeFilename(data.getServiceName()));
            if (!incomingDir.exists()) {
                incomingDir.mkdirs();
            }

            archiveDir = new File(archiveDirName + "/" + escapeFilename(data.getServiceName()));
            if (!archiveDir.exists()) {
                archiveDir.mkdirs();
            }
            for (IArtifactInfo curArtifact : artifactList)
            {

                LOG.info("Received artifact " + curArtifact.getArtifactName());

                handleArtifact(data, data.getServiceName(), null, curArtifact, incomingDir, archiveDir);
            }
        }


        // Process resource level artifacts
        for (IResourceInstance curResource : data.getResources()) {

            LOG.info("Received resource : "+curResource.getResourceName());
            artifactList = curResource.getArtifacts();

            if (artifactList != null) {

                incomingDir = new File(incomingDirName + "/" + escapeFilename(data.getServiceName()) + "/" + escapeFilename(curResource.getResourceName()));
                if (!incomingDir.exists()) {
                    incomingDir.mkdirs();
                }

                archiveDir = new File(archiveDirName + "/" + escapeFilename(data.getServiceName()) + "/" + escapeFilename(curResource.getResourceName()));
                if (!archiveDir.exists()) {
                    archiveDir.mkdirs();
                }
                for (IArtifactInfo curArtifact : artifactList)
                {

                    LOG.info("Received artifact " + curArtifact.getArtifactName());

                    handleArtifact(data, data.getServiceName(), curResource.getResourceName(), curArtifact, incomingDir, archiveDir);
                }
            }
        }

        deployDownloadedFiles(incomingDir, archiveDir, data);


    }


    public void deployDownloadedFiles(File incomingDir, File archiveDir, INotificationData data) {

        if (incomingDir == null) {
            incomingDir = new File(config.getIncomingDir());

            if (!incomingDir.exists()) {
                incomingDir.mkdirs();
            }

        }

        if (archiveDir == null) {
            archiveDir = new File(config.getArchiveDir());

            if (!archiveDir.exists()) {
                archiveDir.mkdirs();
            }
        }

        String curFileName = "";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(incomingDir.toPath())) {
            for (Path file: stream) {
                curFileName = file.toString();
                handleSuccessfulDownload(null,null, null, null, file.toFile(), archiveDir);
            }
        } catch (Exception x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            LOG.warn("Cannot process spool file "+ curFileName, x);
        }

        // Deploy scheduled deployments

        for (int pass = 0 ; pass < NUM_PASSES ; pass++) {

            if (deployList[pass] != null) {
                while (! deployList[pass].isEmpty()) {
                    DeployableArtifact artifact = deployList[pass].pop();

                    DistributionStatusEnum deployResult = DistributionStatusEnum.DEPLOY_ERROR;


                    try {

                        deployResult = deploySpoolFile(artifact);
                    } catch (Exception e) {
                        LOG.error("Caught exception trying to deploy file", e);
                    }


                    IArtifactInfo artifactInfo = artifact.getArtifactInfo();

                    if (artifactInfo != null && data != null) {
                        IDistributionClientResult deploymentStatus;
                            deploymentStatus = client.sendDeploymentStatus(buildStatusMessage(
                                    client, data, artifactInfo,
                                    deployResult));
                    }

                }
            }
        }
    }

    private void handleArtifact(INotificationData data, String svcName, String resourceName, IArtifactInfo artifact, File incomingDir, File archiveDir) {

        // Download Artifact
        IDistributionClientDownloadResult downloadResult = client
                .download(artifact);

        String payload = new String(downloadResult.getArtifactPayload());


        File spoolFile = new File(incomingDir.getAbsolutePath() + "/" + artifact.getArtifactName());

        boolean writeSucceeded = false;

        try {
            FileWriter spoolFileWriter = new FileWriter(spoolFile);
            spoolFileWriter.write(payload);
            spoolFileWriter.close();
            writeSucceeded = true;
        } catch (Exception e) {
            LOG.error("Unable to save downloaded file to spool directory ("+ incomingDir.getAbsolutePath() +")", e);
        }


        if (writeSucceeded && downloadResult.getDistributionActionResult() == DistributionActionResultEnum.SUCCESS) {
            handleSuccessfulDownload(data, svcName, resourceName, artifact, spoolFile, archiveDir);


        } else {
            handleFailedDownload(data, artifact);
        }

    }

    private void handleFailedDownload(INotificationData data,
            IArtifactInfo relevantArtifact) {
        // Send Download Status
        IDistributionClientResult sendDownloadStatus = client
                .sendDownloadStatus(buildStatusMessage(client, data,
                        relevantArtifact, DistributionStatusEnum.DOWNLOAD_ERROR));
    }

    private void handleSuccessfulDownload(INotificationData data, String svcName, String resourceName,
            IArtifactInfo artifact, File spoolFile, File archiveDir) {

        if (data != null && artifact != null) {
            // Send Download Status
            IDistributionClientResult sendDownloadStatus = client
                    .sendDownloadStatus(buildStatusMessage(client, data, artifact, DistributionStatusEnum.DOWNLOAD_OK));
        }

        // If an override file exists, read that instead of the file we just downloaded
        ArtifactTypeEnum artifactEnum = ArtifactTypeEnum.YANG_XML;

        if (artifact != null) {
                artifact.getArtifactType();
        }
        String overrideFileName = config.getOverrideFile();
        if (overrideFileName != null && overrideFileName.length() > 0) {
            File overrideFile = new File(overrideFileName);

            if (overrideFile.exists()) {
                artifactEnum = ArtifactTypeEnum.YANG_XML;
                spoolFile = overrideFile;
            }

        }

        // Process spool file
        Document spoolDoc = null;
        File transformedFile = null;

        // Apply XSLTs and get Doc object
        try {
            transformedFile = applyXslts(spoolFile);
        } catch (Exception e) {
            LOG.error("Caught exception trying to parse XML file", e);
        }

        if (transformedFile != null) {
            try {

                try {

                    DocumentBuilderFactory dbf = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    spoolDoc = db.parse(transformedFile);
                } catch (Exception e) {
                    LOG.error(
                            "Caught exception trying to parse transformed XML file "
                                    + transformedFile.getAbsolutePath(), e);
                }

            } catch (Exception e) {
                LOG.error("Caught exception trying to deploy file", e);
            }
        }


        if (spoolDoc != null) {
            // Analyze file type
            SdncArtifactType artifactType = analyzeFileType(artifactEnum,
                    spoolFile, spoolDoc);

            if (artifactType != SdncArtifactType.UNKNOWN) {

                scheduleDeployment(artifactType, svcName, resourceName, artifact, spoolFile.getName(), transformedFile);

            }

            // SDNGC-2660 : Move file to archive directory even if it is an unrecognized type so that
            // we do not keep trying and failing to process it.
            try {
                Path source = spoolFile.toPath();
                Path targetDir = archiveDir.toPath();

                Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LOG.warn("Could not move "+spoolFile.getAbsolutePath()+" to "+archiveDir.getAbsolutePath(), e);
            }
        }


    }


    private SdncArtifactType analyzeFileType(ArtifactTypeEnum artifactType, File spoolFile, Document spoolDoc) {

        if (artifactType != ArtifactTypeEnum.YANG_XML) {
            LOG.error("Unexpected artifact type - expecting YANG_XML, got "+artifactType);
            return SdncArtifactType.UNKNOWN;
        }

        // Examine outer tag

        try {


            Element root = spoolDoc.getDocumentElement();

            String rootName = root.getTagName();

            if (rootName.contains(":")) {
                String[] rootNameElems = rootName.split(":");
                rootName = rootNameElems[rootNameElems.length - 1];
            }

            if (rootName != null) {
                SdncArtifactType retValue = SdncArtifactType.fromTag(rootName);

                if (retValue == SdncArtifactType.UNKNOWN) {

                    LOG.error("Unexpected file contents - root tag is "+rootName);
                }
                return retValue;
            } else {
                LOG.error("Cannot get root tag from file");
                return SdncArtifactType.UNKNOWN;
            }

        } catch (Exception e) {
            LOG.error("Could not parse YANG_XML file "+spoolFile.getName(), e);
            return SdncArtifactType.UNKNOWN;
        }
    }

    private void scheduleDeployment(SdncArtifactType type, String svcName, String resourceName, IArtifactInfo artifactInfo, String spoolFileName, File spoolFile) {

        if (type.getPass() < deployList.length) {

            if (artifactInfo != null) {
                LOG.debug("Scheduling "+artifactInfo.getArtifactName()+" version "+artifactInfo.getArtifactVersion()+" for deployment");

                deployList[type.getPass()].add(new DeployableArtifact(type, svcName, resourceName, artifactInfo, spoolFile));
            } else {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");//dd/MM/yyyy
                Date now = new Date();
                String artifactVersion = sdfDate.format(now);
                LOG.debug("Scheduling "+spoolFileName+" version "+artifactVersion+" for deployment");
                String artifactName = spoolFileName;
                if (artifactInfo != null) {
                    artifactName = artifactInfo.getArtifactName();
                }
                deployList[type.getPass()].add(new DeployableArtifact(type, svcName, resourceName, artifactName, artifactVersion, spoolFile));
            }
        } else {
            LOG.info("Pass for type "+type.getTag()+" is "+type.getPass()+" which is not <= "+deployList.length);
        }
    }


    private DistributionStatusEnum deploySpoolFile(DeployableArtifact artifact) {

        DistributionStatusEnum deployResult = DistributionStatusEnum.DEPLOY_OK;

        StringBuffer msgBuffer = new StringBuffer();


        String namespace = config.getAsdcApiNamespace();
        if (namespace == null || namespace.length() == 0) {
            namespace="com:att:sdnctl:asdcapi";
        }

        msgBuffer.append("<input xmlns='");
        msgBuffer.append(namespace);
        msgBuffer.append("'>\n");

        String svcName = artifact.getSvcName();
        String resourceName = artifact.getResourceName();
        String artifactName = artifact.getArtifactName();

        if (svcName != null) {
            if (resourceName != null) {
                artifactName = svcName + "/" + resourceName + "/" + artifactName;
            } else {
                artifactName = svcName + "/" + artifactName;
            }
        }

        msgBuffer.append("<artifact-name>"+artifactName+"</artifact-name>\n");
        msgBuffer.append("<artifact-version>"+artifact.getArtifactVersion()+"</artifact-version>\n");


        try {
            BufferedReader rdr = new BufferedReader(new FileReader(artifact.getFile()));

            String curLine = rdr.readLine();

            while (curLine != null) {

                if (!curLine.startsWith("<?")) {
                    msgBuffer.append(curLine+"\n");
                }
                curLine = rdr.readLine();
            }
            rdr.close();

        } catch (Exception e) {
            LOG.error("Could not process spool file "+artifact.getFile().getName(), e);
            return DistributionStatusEnum.DEPLOY_ERROR;
        }

        msgBuffer.append("</input>\n");


        byte[] msgBytes = msgBuffer.toString().getBytes();

        Document results = postRestXml(artifact.getType().getRpcUrl(config.getAsdcApiBaseUrl()), msgBytes);

        if (results == null) {

            deployResult = DistributionStatusEnum.DEPLOY_ERROR;
        } else {

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();

            String asdcApiResponseCode = "500";

            try {

                asdcApiResponseCode = xp.evaluate("//asdc-api-response-code[position()=1]/text()", results.getDocumentElement());
            } catch (Exception e) {
                LOG.error("Caught exception retrying to evaluate xpath", e);
            }

            if (asdcApiResponseCode.contains("200")) {
                LOG.info("Update to SDN-C succeeded");
                deployResult = DistributionStatusEnum.DEPLOY_OK;
            } else {
                LOG.info("Update to SDN-C failed (response code "+asdcApiResponseCode+")");

                if (asdcApiResponseCode.contains("409")) {
                    deployResult = DistributionStatusEnum.ALREADY_DEPLOYED;
                } else {

                    deployResult = DistributionStatusEnum.DEPLOY_ERROR;
                }
            }
        }



        return deployResult;
    }





    public static IDistributionStatusMessage buildStatusMessage(
            final IDistributionClient client, final INotificationData data,
            final IArtifactInfo relevantArtifact,
            final DistributionStatusEnum status) {
        IDistributionStatusMessage statusMessage = new IDistributionStatusMessage() {

            @Override
			public long getTimestamp() {
                long currentTimeMillis = System.currentTimeMillis();
                return currentTimeMillis;
            }

            @Override
			public DistributionStatusEnum getStatus() {
                return status;
            }

            @Override
			public String getDistributionID() {
                return data.getDistributionID();
            }

            @Override
			public String getConsumerID() {
                return client.getConfiguration().getConsumerID();
            }

            @Override
			public String getArtifactURL() {
                return relevantArtifact.getArtifactURL();
            }
        };
        return statusMessage;

    }

    private HttpURLConnection getRestXmlConnection(String urlString, String method) throws IOException
    {
        URL sdncUrl = new URL(urlString);
        Authenticator.setDefault(new SdncAuthenticator(config.getSdncUser(), config.getSdncPasswd()));

        HttpURLConnection conn = (HttpURLConnection) sdncUrl.openConnection();

        String authStr = config.getSdncUser()+":"+config.getSdncPasswd();
        String encodedAuthStr = new String(Base64.encodeBase64(authStr.getBytes()));

        conn.addRequestProperty("Authentication", "Basic "+encodedAuthStr);

        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/xml");
        conn.setRequestProperty("Accept", "application/xml");

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        return conn;

    }

    private Document postRestXml(String urlString, byte[] msgBytes) {
        Document response = null;

        LOG.info("Sending REST POST to "+urlString);
        LOG.info("Message body:\n"+new String(msgBytes));

        try {
            HttpURLConnection conn = getRestXmlConnection(urlString, "POST");

            if (conn instanceof HttpsURLConnection) {
                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                ((HttpsURLConnection)conn).setHostnameVerifier(hostnameVerifier);
            }

            // Write message
            conn.setRequestProperty("Content-Length", ""+msgBytes.length);
            DataOutputStream outStr = new DataOutputStream(conn.getOutputStream());
            outStr.write(msgBytes);
            outStr.close();


            // Read response
            BufferedReader respRdr;

            LOG.info("Response: "+conn.getResponseCode()+" "+conn.getResponseMessage());


            if (conn.getResponseCode() < 300) {

                respRdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                respRdr = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuffer respBuff = new StringBuffer();

            String respLn;

            while ((respLn = respRdr.readLine()) != null) {
                respBuff.append(respLn+"\n");
            }
            respRdr.close();

            String respString = respBuff.toString();

            LOG.info("Response body :\n"+respString);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();


            response = db.parse(new ByteArrayInputStream(respString.getBytes()));

        } catch (Exception e) {

            LOG.error("Caught exception in postRestXml", e);
        }

        return response;
    }

    private File applyXslts(File srcFile) {

        Document doc = null;


        File inFile = srcFile;
        File outFile = null;

        String xsltPathList = config.getXsltPathList();

        if (xsltPathList == null || xsltPathList.length() == 0) {
            outFile = inFile;
        } else {

            String[] xsltPaths = xsltPathList.split(",");

            for (String xsltPath : xsltPaths) {
                try{

                    outFile = File.createTempFile("tmp", "xml");
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Source xslt = new StreamSource(new File(xsltPath));
                    Transformer transformer = factory.newTransformer(xslt);
                    Source text = new StreamSource(inFile);


                    transformer.transform(text, new StreamResult(outFile));

                    inFile = outFile;

                } catch (Exception e) {
                    LOG.error("Caught exception trying to apply XSLT template "+xsltPath, e);

                }

            }
        }

        // After transformations, parse transformed XML


        return outFile;
    }

    private String escapeFilename(String str) {
        StringBuffer retval = new StringBuffer();

        for (int i = 0 ; i < str.length() ; i++) {
            char curchar = str.charAt(i);
            if (Character.isJavaIdentifierPart(curchar)) {
                retval.append(curchar);
            }
        }

        return retval.toString();

    }

}
