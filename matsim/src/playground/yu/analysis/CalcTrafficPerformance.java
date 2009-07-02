/* *********************************************************************** *
 * project: org.matsim.*
 * CalcTrafficPerformence.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package playground.yu.analysis;

import org.matsim.core.events.LinkEnterEvent;
import org.matsim.core.events.handler.LinkEnterEventHandler;
import org.matsim.core.network.LinkImpl;
import org.matsim.core.network.NetworkLayer;
import org.matsim.roadpricing.RoadPricingScheme;

import playground.yu.utils.TollTools;

/**
 * Calculates the traffic performance [Per.km] and works in current modell of
 * MATSim only for private transport
 * 
 * @author ychen
 * 
 */
public class CalcTrafficPerformance implements LinkEnterEventHandler {
	private double lengthSum = 0;
	private NetworkLayer network = null;
	private RoadPricingScheme toll = null;

	public CalcTrafficPerformance(final NetworkLayer network) {
		this.network = network;
	}

	public CalcTrafficPerformance(NetworkLayer network, RoadPricingScheme toll) {
		this(network);
		this.toll = toll;
	}

	public void handleEvent(LinkEnterEvent event) {
		LinkImpl l = event.getLink();
		if (l == null) {
			l = this.network.getLink(event.getLinkId().toString());
		}
		if (l != null) {
			if (toll == null)
				this.lengthSum += l.getLength() / 1000.0;
			else if (TollTools.isInRange(l, toll))
				this.lengthSum += l.getLength() / 1000.0;
		}
	}

	public void reset(int iteration) {
		this.lengthSum = 0;
	}

	/**
	 * @return Traffic performance [Per*km]
	 */
	public double getTrafficPerformance() {
		return this.lengthSum;
	}
}
