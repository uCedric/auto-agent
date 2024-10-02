import ollama from 'ollama';
import axios from 'axios';

class Embed {
    ollamaInstance;
    model;

    constructor(embed){
        this.ollamaInstance = ollama;
        this.embed = embed;
    };

    async embedToVector(chunk){
        return await this.ollamaInstance.embeddings({ model: 'nomic-embed-text', prompt: chunk });
    }

    static async init(embed){
        try{
            if(!await Embed.isConnected())throw new Error('Ollama is not connected');
            if(!await Embed.isModelExist(embed))throw new Error('embeding model does not exist');
    
            return new Embed(embed);
        }catch(error){
            console.log(error.message);
        }
    }

    static async isConnected(){
        return (await axios.get('http://127.0.0.1:11434/')).status === 200;
    }

    static async isModelExist(embed){
        const {models: modelList} = await ollama.list();

        for(let modelItem of modelList){
            if(modelItem.name === embed)return true;
        }

        return false;
    }
}

export default class embedSingleton {

    static async getInstance(embed){
        if(!embedSingleton.instance){
            embedSingleton.instance = await Embed.init(embed);
            console.log("embedSingleton instance is created");
        }

        return embedSingleton.instance;
    }
}

export const initializeEmbed = async (embed) => embedSingleton.getInstance(embed);