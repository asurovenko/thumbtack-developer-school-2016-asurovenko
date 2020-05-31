var Todo = require('../src/js/model/Todo');

describe("Model", function() {
  describe("create", function () {
    before(function() {
      this.todo = new Todo({
        text: 'test text1',
        status: 0
      });
    });

    it('should contains correct attributes', function() {
      expect(this.todo).to.have.property('text', 'test text1');
      expect(this.todo).to.have.property('status', 0);
      expect(this.todo).to.have.property('hot', 0);
      expect(this.todo).to.have.property('uniqueId', 'model-1');
    });
  });
});
