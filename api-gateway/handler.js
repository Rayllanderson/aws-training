'use strict';

const pacientes = [
  { id: 1, nome: 'Kaguya Shinomiya', dataNascimento: '2004-01-01' },
  { id: 2, nome: 'Hayasaka Ai', dataNascimento: '2003-04-02' },
  { id: 3, nome: 'Chika Fujiwara', dataNascimento: '2003-03-03' }
]

module.exports.listarPacientes = async (event) => {
  return {
    statusCode: 200,
    body: JSON.stringify(pacientes),
  };

  // Use this code if you don't use the http event with the LAMBDA-PROXY integration
  // return { message: 'Go Serverless v1.0! Your function executed successfully!', event };
};

module.exports.buscaPorId = async (event) => {
  const { pacienteId } = event.pathParameters

  const pacienteEncontrado = pacientes.find(paciente => paciente.id == pacienteId)

  if (pacienteEncontrado) {
    return {
      statusCode: 200,
      body: JSON.stringify(pacienteEncontrado),
    };
  }else {
    return {
      statusCode: 404,
      body: JSON.stringify({error: "Não encontrado"}),
    };
  }



  // Use this code if you don't use the http event with the LAMBDA-PROXY integration
  // return { message: 'Go Serverless v1.0! Your function executed successfully!', event };
};