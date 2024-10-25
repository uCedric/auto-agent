import baseModel from './baseModel.js';
import pgvector from 'pgvector/pg';

const addChunk = async (documentUuid, vector, chunk) => {
    const sql = `INSERT INTO api.chunks (document_uuid, vector, content) VALUES ($1, $2, $3)`;
    const values = [documentUuid, pgvector.toSql(vector), chunk];
    
    return baseModel.executeQuery(sql, values);
}; 

const getRelatedChunks = async (vector) => {
    const sql = `SELECT content FROM api.chunks ORDER BY vector <=> $1 LIMIT 5`;
    const values = [pgvector.toSql(vector)];
    
    return baseModel.executeQuery(sql, values);
};

export default {addChunk, getRelatedChunks};