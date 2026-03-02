package com.example.day1.memo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class MemoService {

	private final MemoRepository memoRepository;

	public MemoService(MemoRepository memoRepository) {
		this.memoRepository = memoRepository;
	}

	@Transactional
	public MemoResponse create(MemoCreateRequest request) {
		if (request.title() == null || request.title().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title must not be blank");
		}

		if (request.content() == null || request.content().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content must not be blank");
		}

		Memo memo = memoRepository.save(new Memo(request.title(), request.content()));
		return MemoResponse.from(memo);
	}

	public List<MemoResponse> findAll() {
		return memoRepository.findAll()
			.stream()
			.map(MemoResponse::from)
			.toList();
	}

	public MemoResponse findById(Long id) {
		return MemoResponse.from(getMemo(id));
	}

	@Transactional
	public void delete(Long id) {
		memoRepository.delete(getMemo(id));
	}

	private Memo getMemo(Long id) {
		return memoRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "memo not found"));
	}
}
