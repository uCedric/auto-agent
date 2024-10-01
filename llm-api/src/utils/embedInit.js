import '@tensorflow/tfjs';
import * as use from '@tensorflow-models/universal-sentence-encoder';
//import tfjs-node or tfjs-node-gpu is considerable

class Embed {
    constructor(model){
        this.model = model;
    };

    static async init(){
        const model= await use.load();

        const instance = new Embed(model);

        return instance;
    }

    async embedToVector(chunks){
        const vectors = await this.model.embed(chunks).then(embeddings => embeddings.arraySync());
        
        return vectors;
    }
}

export default class embedSingleton {

    static async getInstance(){
        if(!embedSingleton.instance){
            console.log("embedSingleton instance is not created yet");
            embedSingleton.instance = await Embed.init();
            console.log("embedSingleton instance is created");
        }

        return embedSingleton.instance;
    }
}

export const initializeEmbed = async () => embedSingleton.getInstance();