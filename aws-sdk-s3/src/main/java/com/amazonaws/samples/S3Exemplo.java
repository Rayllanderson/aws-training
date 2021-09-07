package com.amazonaws.samples;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import java.io.File;

public class S3Exemplo {

    private static final String accessKey = "SUA_ACCESS_KEY_AKI";
    private static final String secretKey = "SUA_SECRET_KEY_AKI";
    private static final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    private static final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

    public static void main(String[] args) {

        System.out.println("Criando bucket ...");
        String bucketName = "test-s3-sdk";
        criaBucket(bucketName);

        System.out.println("Listando buckets ...");
        listaBuckets();

        System.out.println("Enviando arquivo...");
        File file = new File("amazon.png");
        enviaArquivoParaBucket(bucketName, "amazon-s3.png", file);

        System.out.println("Listando objetos do bucket ...");
        listaObjetosDoBucket(bucketName);

        System.out.println("Deletando objeto ...");
        deletaObjetoDoBucket(bucketName, "amazon-s3.png");

        listaObjetosDoBucket(bucketName); //confirmando que excluiu

        s3.deleteBucket(bucketName);
    }

    private static void criaBucket(String nome) {
        s3.createBucket(nome);
    }

    private static void listaBuckets() {
        s3.listBuckets().forEach(bucket -> System.out.println(bucket.getName()));
    }

    private static void enviaArquivoParaBucket(String bucketName, String nomeDoArquivo, File file) {
        s3.putObject(bucketName, nomeDoArquivo, file);
    }

    private static void listaObjetosDoBucket(String bucketName) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        ObjectListing listObjects = s3.listObjects(listObjectsRequest);
        listObjects.getObjectSummaries().forEach(objectSummary -> System.out.println(objectSummary.getKey() + " - " + objectSummary.getSize()));
    }

    private static void deletaObjetoDoBucket(String bucketName, String nomeDoArquivo) {
        s3.deleteObject(bucketName, nomeDoArquivo);
    }
}
