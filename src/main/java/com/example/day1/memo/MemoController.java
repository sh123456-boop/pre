package com.example.day1.memo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

	private final MemoService memoService;

	public MemoController(MemoService memoService) {
		this.memoService = memoService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MemoResponse create(@RequestBody MemoCreateRequest request) {
		return memoService.create(request);
	}

	@GetMapping
	public List<MemoResponse> findAll() {
		return memoService.findAll();
	}

	@GetMapping("/{id}")
	public MemoResponse findById(@PathVariable Long id) {
		return memoService.findById(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		memoService.delete(id);
	}
}
