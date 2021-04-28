import { userPostsPipe } from './userPost.pipe';

describe('userPostsPipe', () => {
  it('create an instance', () => {
    const pipe = new userPostsPipe();
    expect(pipe).toBeTruthy();
  });
});
