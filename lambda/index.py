import boto3

s3 = boto3.resource('s3')
client = boto3.client('rekognition')
bucket_name = 'fa-imagens-2'


# ir no bucket s3 e recuperar as imagens
def lista_imagens():
    imagens = []
    bucket = s3.Bucket(bucket_name)
    for imagem in bucket.objects.all():
        imagens.append(imagem.key)
    return imagens


def indexa_colecao(imagens):
    for i in imagens:
        response = client.index_faces(
            CollectionId='faces',
            DetectionAttributes=[
            ],
            ExternalImageId=i[:-4],
            Image={
                'S3Object': {
                    'Bucket': bucket_name,
                    'Name': i,
                }
            },
        )


images = lista_imagens()
indexa_colecao(images)
