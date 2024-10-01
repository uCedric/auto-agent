import baseModel from './baseModel.js';
import pgvector from 'pgvector/pg';

const addChunk = async ({vector}) => {
    const sql = `INSERT INTO api.chunks (id, vector, content) VALUES (1, $1, 'test') RETURNING *`;
    const values = [pgvector.toSql(vector)];
    
    return baseModel.executeQuery(sql, values);
}; 

export default {addChunk};