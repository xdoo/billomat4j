/*
 * Copyright 2012 Oliver Siegmar <oliver@siegmar.net>
 *
 * This file is part of Billomat4J.
 *
 * Billomat4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Billomat4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.siegmar.billomat4j.sdk.unit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.unit.Unit;
import net.siegmar.billomat4j.sdk.domain.unit.UnitFilter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class UnitServiceTest extends AbstractServiceTest {

    private final List<Unit> createdUnits = new ArrayList<>();

    @AfterMethod
    public void cleanup() {
        for (final Unit unit : createdUnits) {
            unitService.deleteUnit(unit.getId());
        }
        createdUnits.clear();
    }

    @Test
    public void findAll() {
        assertTrue(unitService.findUnits(null).isEmpty());
        createUnit("Test Unit 1 (findAll)");
        assertFalse(unitService.findUnits(null).isEmpty());
    }

    private Unit createUnit(final String name) {
        final Unit unit = new Unit();
        unit.setName(name);
        unitService.createUnit(unit);
        createdUnits.add(unit);

        return unit;
    }

    @Test
    public void findFiltered() {
        createUnit("Test Unit 1 (findFiltered)");
        createUnit("Test Unit 2 (findFiltered)");

        final List<Unit> units = unitService.findUnits(new UnitFilter().byName("Test Unit 1 (findFiltered)"));
        assertEquals(units.size(), 1);
        assertEquals(units.get(0).getName(), "Test Unit 1 (findFiltered)");
    }

    @Test
    public void create() {
        final Unit unit = createUnit("Test Unit 1 (create)");
        assertNotNull(unit.getId());
    }

    @Test
    public void update() {
        final Unit unit = createUnit("Test Unit 1 (update)");
        assertNotNull(unit.getId());

        unit.setName("Test Unit 1 (updated)");
        unitService.updateUnit(unit);
        assertEquals(unit.getName(), "Test Unit 1 (updated)");
        assertEquals(unitService.getUnitById(unit.getId()).getName(), "Test Unit 1 (updated)");
    }

}
