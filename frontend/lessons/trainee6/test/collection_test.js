var TodoCollection = require('../src/js/collection/Todo');
var TodoModel = require('../src/js/model/Todo');

describe("Collection", function() {

  before(function() {
    this.todoCollection = new TodoCollection([
      {
        text: 'test text1',
        status: 0
      },
      {
        text: 'test text2',
        status: 1
      }
    ]);
  });

  describe("create", function () {

    it('should contains 2 models', function() {
      expect(this.todoCollection.data).to.have.length(2);
    });

    it('should contains model instanceof TodoModel', function() {
      expect(this.todoCollection.data[0]).to.be.instanceof(TodoModel);
    });
  });


  describe("add one model", function () {
    before(function() {
	  this.initialLength = this.todoCollection.data.length;	
      this.todoCollection.add({
        text: 'test text3',
        status: 1
      });
    });

    it('should contains one more model', function() {
      expect(this.todoCollection.data).to.have.length(this.initialLength + 1);
    });
  });
  
  describe("remove existing model", function () {
	 before(function() {
		this.initialLength = this.todoCollection.data.length;
		this.todoModel = this.todoCollection.data[0]; 
		this.todoCollection.remove(this.todoModel.uniqueId);
	 });
	 
	 it('should contains one less model', function() {
		expect(this.todoCollection.data).to.have.length(this.initialLength - 1); 
	 });
  });
  
  describe("remove not existing model", function () {
	before(function() {
		this.todoModel = new TodoModel({
			text: 'Not existing model',
			status: -1
		});
		this.initialLength = this.todoCollection.data.length;
		this.todoCollection.remove(this.todoModel.uniqueId);
	});

	it("should contains the same models count", function () {
		expect(this.todoCollection.data).to.have.length(this.initialLength);
	});
	
	it("no exception should be raised", function () {
		expect(this.todoCollection).to.respondTo("remove");
		expect(this.todoCollection.remove).to.not.throw(Error);
	});
  });
});
