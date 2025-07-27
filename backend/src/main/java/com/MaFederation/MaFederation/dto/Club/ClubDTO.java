package com.MaFederation.MaFederation.dto.Club;

import java.util.List;

public record ClubDTO (
    Integer clubId,
    String name,
    String location,
    Integer foundedYear,
    String contactEmail,
    String contactPhone,
    String bankAccount,
    String bankName,
    List<Integer> categoryIds,     
    List<Integer> memberIds,
    List<Integer> fileIds 
) {}
