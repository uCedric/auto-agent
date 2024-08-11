import config from "config";

import pg from "pg";
const { Pool } = pg;

const POSTGRES_CONNECTION_INFO = config.get("data.postgres");
const host = POSTGRES_CONNECTION_INFO.host || "127.0.0.1";
const port = POSTGRES_CONNECTION_INFO.port || "8000";
const user = POSTGRES_CONNECTION_INFO.user || "user";
const password = POSTGRES_CONNECTION_INFO.password || "password";
const name = POSTGRES_CONNECTION_INFO.name || "as-db";

//listener to monitor connection status
const PG_POOL_EVENTS = {
    CONNECT: "connect",
    ACQUIRE: "acquire",
    REMOVE: "remove",
    ERROR: "error"
};

const PG_CLIENT_EVENTS = {
    END: "end",
    ERROR: "error",
    DRAIN: "drain"
};

//cedric: config.get...
const RETRY_IN_MILLISEC = POSTGRES_CONNECTION_INFO.retry || 1000;

class DB {
   constructor({ host, port, user, password, name } = {}) {
        this.pool = new Pool({host, port, user, password, name});

        this.pool
                .on(PG_POOL_EVENTS.CONNECT, (client) => {
                    console.info(`[${client.database}] pool is connecting`);

                    client.on(PG_CLIENT_EVENTS.ERROR, (err) => {
                        console.log(`[${client.database} connection error]`, err);
                    });

                    client.on(PG_CLIENT_EVENTS.END, () => {
                        console.info(`[${client.database}] client connection is ending`);
                    });
                })
                .on(PG_POOL_EVENTS.ACQUIRE, (client) => {
                    console.info(`[${client.database}] pool is acquiring`);
                })
                .on(PG_POOL_EVENTS.REMOVE, (client) => {
                    const { totalCount, idleCount, waitingCount } = this.pool;
                    console.info(
                        `[${client.database}] pool is removing, client totalCount: ${totalCount}, client idleCount: ${idleCount}, waitingCount: ${waitingCount}`
                    );
                })
                .on(PG_POOL_EVENTS.ERROR, (err, client) => {
                    console.log(`database pool error on db: ${client.database}`, err);
                });
   }

   async query(sql, value = null){
        try{
            const client = await this.buildConnection();
            return isEmpty(value) ? await client.query(sql) : await client.query(sql, value);
        }catch(error){
            console.log(`[db] query fail: `, error);
        }finally{
            client.release();
            console.info("[db] query close")
        }
   };

   async buildConnection(){
        let client;
        try{
            client = await this.pool.connect();
        }catch(error){
            console.log(`[db] connection fail: `, error);
            console.info(`[db] retry another connection after ${RETRY_IN_MILLISEC} millisec`);
            await this.pool.connect();
        }

        return client;
   }
}

export default class DatabaseSingleton{
    constructor(){
        console.log("Use DatabaseSingleton.getInstance() instead of new DatabaseSingleton()");
    }

    static getInstance({ host, port, user, password, name } = {}){
        if(!DatabaseSingleton.instance){
            DatabaseSingleton.instance = new DB({ host, port, user, password, name });
            console.info("DB connection singleton is created");
        }

        return DatabaseSingleton.instance;
    }
};

export const initializeDB = () => DatabaseSingleton.getInstance({ host, port, user, password, name });