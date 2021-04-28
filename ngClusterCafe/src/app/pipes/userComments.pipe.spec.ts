import { userCommentsPipe } from './userComments.pipe';

describe('userCommentsPipe', () => {
  it('create an instance', () => {
    const pipe = new userCommentsPipe();
    expect(pipe).toBeTruthy();
  });
});
