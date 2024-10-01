import promptService from "../services/prompt.service.js";

const promptQuery = async (req, res) => {
    const { content: prompt } = req.body;

    res.writeHead(200, {
        'Content-Type': 'text/plain',
        'Transfer-Encoding': 'chunked',
    });

    const promptRes = promptService.promptQuery({prompt, res});

    try {
        for await (const part of promptRes) {
            res.write(part);
        }
    } catch (error) {
        console.error('Error during streaming:', error);
        res.writeHead(500);
        res.end('Internal Server Error');
    }finally{
        res.end();
    }
};

export default { promptQuery };
