package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.mappers.ReserveMapper;
import com.vinisnzy.cinema.models.reserve.Reserve;
import com.vinisnzy.cinema.models.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.models.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.repositories.ReserveRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;
    private final SessionService sessionService;
    private final SeatService seatService;

    public List<ReserveResponseDTO> getAllReserves() {
        return reserveRepository.findAllWithSeats().stream()
                .map(reserve -> reserveMapper.toResponseDTO(reserve, sessionService)).toList();
    }

    public ReserveResponseDTO getReserveById(UUID id) {
        Reserve reserve = getEntityById(id);
        return reserveMapper.toResponseDTO(reserve, sessionService);
    }

    public ReserveResponseDTO createReserve(ReserveRequestDTO data) {
        Reserve reserve = reserveMapper.toEntity(data, sessionService, seatService);
        return reserveMapper.toResponseDTO(reserveRepository.save(reserve), sessionService);
    }

    public ReserveResponseDTO updateReserve(UUID id, ReserveRequestDTO data) {
        Reserve reserve = getEntityById(id);
        reserve.setClientName(data.clientName());
        return reserveMapper.toResponseDTO(reserveRepository.save(reserve), sessionService);
    }

    @Transactional
    public void deleteReserve(UUID id) {
        Reserve reserve = reserveRepository.findByIdWithSeats(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserve not found"));
        seatService.releaseSeatsFromReserve(reserve);
        reserveRepository.delete(reserve);
    }

    public Reserve getEntityById(UUID id) {
        return reserveRepository.findByIdWithSeats(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserve not found"));
    }
}
