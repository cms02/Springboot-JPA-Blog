let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 !
			this.save();
		});
		$("#btn-delete").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 !
			this.deleteById();
		});
		$("#btn-update").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 !
			this.update();
		});
		$("#btn-reply-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 !
			this.replySave();
		});

	},

	save: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});
	},
	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});
	},

	update: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});
	},
	replySave: function() {
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};

		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("댓글작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();