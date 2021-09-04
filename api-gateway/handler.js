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
  try {

    const { pacienteId } = event.pathParameters

    const data = await dynamoDb.get({
      ...params,
      Key: {
        paciente_id: pacienteId
      }
    }).promise()

    const pacienteEncontrado = data.Item

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
};

module.exports.cadastraPaciente = async (event) => {
  try {
    const dados = JSON.parse(event.body)

    const { nome, email, dataNascimento } = dados

    const hoje = new Date().getTime()
    const pacienteASerSalvo = {
      paciente_id: uuidv4(),
      nome: nome,
      email: email,
      dataNascimento: dataNascimento,
      criadoEm: hoje,
      atualizadoEm: hoje
    }
    
    await dynamoDb.put({
      ... params,
      Item: pacienteASerSalvo
    }).promise()

    return {
      statusCode: 201
    }

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


module.exports.atualizaPaciente = async (event) => {
  try {
    const { paciente_id } = event.pathParameters

    const dados = JSON.parse(event.body);

    const { nome, dataNascimento, email } = dados;

    await dynamoDb
      .update({
        ...params,
        Key: {
          paciente_id: paciente_id
        },
        UpdateExpression:
          'SET nome = :nome, dataNascimento = :dataNascimento, email = :email,'
          + ' atualizadoEm = :atualizadoEm',
        ConditionExpression: 'attribute_exists(paciente_id)',
        ExpressionAttributeValues: {
          ':nome': nome,
          ':dataNascimento': dataNascimento,
          ':email': email,
          ':atualizadoEm': new Date().getTime()
        }
      }).promise()

    return {
      statusCode: 204,
    };
  } catch (err) {
    console.log("Error", err);

    let error = err.name ? err.name : "Exception";
    let message = err.message ? err.message : "Unknown error";
    let statusCode = err.statusCode ? err.statusCode : 500;

    if (error == 'ConditionalCheckFailedException') {
      error = 'Paciente não existe';
      message = `Recurso com o ID ${pacienteId} não existe e não pode ser atualizado`;
      statusCode = 404;
    }

    return {
      statusCode,
      body: JSON.stringify({
        error,
        message
      }),
    };
  }
};






function uuidv4() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}
