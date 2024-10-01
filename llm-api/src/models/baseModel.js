import dbConn from '../utils/dbConn.js';

const executeQuery = async (sql, values = null, dbClient = null) => {
    const dbInstance = dbClient || dbConn.getInstance();
    return dbInstance.query(sql, values);
};

export default { executeQuery };