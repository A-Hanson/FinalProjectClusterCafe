import { FlaggedPipe } from './flagged.pipe';

describe('FlaggedPipe', () => {
  it('create an instance', () => {
    const pipe = new FlaggedPipe();
    expect(pipe).toBeTruthy();
  });
});
