import boto3
import json

s3 = boto3.resource('s3')
client = boto3.client('rekognition')
bucket_name = 'fa-imagens-2'


def detecta_faces():
    return client.index_faces(
        CollectionId='faces',
        DetectionAttributes=['DEFAULT'],
        ExternalImageId="img-temp",
        Image={
            'S3Object': {
                'Bucket': bucket_name,
                'Name': "kaguya2.jpg",
            }
        },
    )


def cria_lista_de_faceId(faces_detectadas):
    faces_id = []
    for imagem in range(len(faces_detectadas['FaceRecords'])):
        faces_id.append(faces_detectadas['FaceRecords'][imagem]['Face']['FaceId'])
    return faces_id


def compara_imagens(faces_id):
    resultado_comparacao = []
    for id in faces_id:
        resultado_comparacao.append(
            client.search_faces(
                CollectionId='faces',
                FaceId=id,
                FaceMatchThreshold=80,
                MaxFaces=10
            )
        )
    return resultado_comparacao


faces_detectadas = detecta_faces()
faces_id = cria_lista_de_faceId(faces_detectadas)
resultado_comparacao = compara_imagens(faces_id)

print(json.dumps(resultado_comparacao, indent=4))
