import config from "config";

import isEmpty from "lodash-es/isEmpty.js";

import pg from "pg";
const { Pool } = pg;

const host = "172.17.117.139";
const connectionString = "postgres://user:password@172.17.117.139:5432/api";
const port = "5432";
const user = "user";
const password = "password";
const name = "api";

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
const RETRY_IN_MILLISEC = 1000;

class DB {
   constructor({ host, connectionString, port, user, password, name } = {}) {
        this.pool = new Pool({host, connectionString, port, user, password, name});

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
        let client;
        try{
            client = await this.buildConnection();
            return isEmpty(value) ? await client.query(sql) : await client.query(sql, value);
        }catch(error){
            console.log(`[db] query fail: `, error);
        }finally{
            if (client) {
                client.release();
                console.info("[db] query close")
            }
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

    static getInstance({ host, connectionString, port, user, password, name } = {}){
        if(!DatabaseSingleton.instance){
            DatabaseSingleton.instance = new DB({ host, connectionString, port, user, password, name });
            
            console.info("DB connection singleton is created");
        }

        return DatabaseSingleton.instance;
    }
};

export const initializeDB = () => DatabaseSingleton.getInstance({ host, connectionString, port, user, password, name });