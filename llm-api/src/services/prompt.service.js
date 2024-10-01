import llmConn from '../utils/llmInit.js';
async function* promptQuery({prompt}) {
    const llmInstance = await llmConn.getInstance({model: 'gemma2:2b'});
    const promptRes = llmInstance.inference({prompt});

    for await (const part of promptRes) {
        process.stdout.write(part);
        yield part;
    }
}

export default { promptQuery };