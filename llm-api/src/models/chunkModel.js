import baseModel from './baseModel.js';
import pgvector from 'pgvector/pg';

const addChunk = async (vector, chunk) => {
    const sql = `INSERT INTO api.chunks (vector, content) VALUES ($1, $2) RETURNING *`;
    const values = [pgvector.toSql(vector), chunk];
    
    return baseModel.executeQuery(sql, values);
}; 

export default {addChunk};