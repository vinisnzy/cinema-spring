package com.vinisnzy.cinema.dtos;

import java.util.List;

public record CustomPageDTO<T>(
        List<T> content,
        int page,
        int totalPages,
        long totalElements
) {
}
