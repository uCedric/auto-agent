import baseModel from './baseModel.js';
import pgvector from 'pgvector/pg';

const addChunk = async (documentUuid, vector, chunk) => {
    const sql = `INSERT INTO api.chunks (document_uuid, vector, content) VALUES ($1, $2, $3) RETURNING *`;
    const values = [documentUuid, pgvector.toSql(vector), chunk];
    
    return baseModel.executeQuery(sql, values);
}; 

export default {addChunk};