'use strict';

const AWS = require('aws-sdk')

const dynamoDb = new AWS.DynamoDB.DocumentClient()
const params = {
  TableName: 'PACIENTES'
}

module.exports.listarPacientes = async (event) => {
  try {
    const data = await dynamoDb.scan(params).promise();
    const pacientes = data.Items
    return {
      statusCode: 200,
      body: JSON.stringify(pacientes),
    };
  } catch (err) {
    console.log("Erro...", err)
    return {
      statusCode: err.statusCode ? err.statusCode : 500,
      body: JSON.stringify({
        error: err.name ? err.name : "Ocorreu um erro",
        message: err.message ? err.message : "Erro desconhecido"
      }),
    };
  };
}

module.exports.buscaPorId = async (event) => {
  const { pacienteId } = event.pathParameters

  const pacienteEncontrado = pacientes.find(paciente => paciente.id == pacienteId)

  if (pacienteEncontrado) {
    return {
      statusCode: 200,
      body: JSON.stringify(pacienteEncontrado),
    };
  } else {
    return {
      statusCode: 404,
      body: JSON.stringify({ error: "Não encontrado" }),
    };
  }



  // Use this code if you don't use the http event with the LAMBDA-PROXY integration
  // return { message: 'Go Serverless v1.0! Your function executed successfully!', event };
};