package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.ReserveMapper;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.dtos.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.repositories.ReserveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository repository;
    private final ReserveMapper reserveMapper;
    private final SessionService sessionService;
    private final SeatService seatService;

    public CustomPageDTO<ReserveResponseDTO> getAllReserves(Pageable pageable) {
        Page<Reserve> page = repository.findAllWithSeats(pageable);
        List<ReserveResponseDTO> content = page.getContent().stream()
                .map(reserve -> reserveMapper.toResponseDTO(reserve, sessionService))
                .toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public ReserveResponseDTO getReserveById(UUID id) {
        Reserve reserve = getEntityById(id);
        return reserveMapper.toResponseDTO(reserve, sessionService);
    }

    @Transactional
    public ReserveResponseDTO createReserve(ReserveRequestDTO data) {
        Reserve reserve = reserveMapper.toEntity(data, sessionService, seatService);
        return reserveMapper.toResponseDTO(repository.save(reserve), sessionService);
    }

    public ReserveResponseDTO updateReserve(UUID id, ReserveRequestDTO data) {
        Reserve reserve = getEntityById(id);
        reserve.setClientName(data.clientName());
        return reserveMapper.toResponseDTO(repository.save(reserve), sessionService);
    }

    @Transactional
    public void deleteReserve(UUID id) {
        Reserve reserve = getEntityById(id);
        seatService.releaseSeatsFromReserve(reserve);
        repository.delete(reserve);
    }

    public Reserve getEntityById(UUID id) {
        return repository.findByIdWithSeats(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserve not found, id: " + id));
    }
}
