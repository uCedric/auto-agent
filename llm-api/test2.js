import '@tensorflow/tfjs';
import * as use from '@tensorflow-models/universal-sentence-encoder';

// Load the model.
// use.load().then(model => {
//     // Embed an array of sentences.
//     console.log('model loaded');
//     const sentences = [
//       'hate',
//       'love'
//     ];
//     model.embed(sentences).then(async embeddings => {
//       // `embeddings` is a 2D tensor consisting of the 512-dimensional embeddings for each sentence.
//       // So in this example `embeddings` has the shape [2, 512].
//       embeddings.print(true /* verbose */);
//     });
//   });


const sentences = [
        'hate',
        'love',
        'fear'
      ];
const model = await use.load();

const result = await model.embed(sentences)

console.log(result.arraySync());