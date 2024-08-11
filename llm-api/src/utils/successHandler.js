export default (_req, res, next) => {
    res.success = (obj = {}) => {
        const { message, data } = { message: "Successful Request", data: obj };
        res.send({ message, data }); 
    };
    next();
};
