package edu.ufl.cise.cs1.robots;

import robocode.*;
import java.awt.Color;

public class sunsetpink extends TeamRobot
{

    @java.lang.Override
    public void run() { //https://sourceforge.net/projects/robocode/
        setAllColors(Color.pink);
        while (true) {
            turnGunRight(360);
            setTurnLeft(10000);

            setMaxVelocity(5);

            ahead(10000);

        }
    }

    @java.lang.Override
    public void onBulletMissed(BulletMissedEvent event) {
        scan();
    }

    @java.lang.Override
    public void onHitByBullet(HitByBulletEvent event) {
        setTurnLeft(70);
        setAhead(200);
        setTurnRight(125);
    }

    @java.lang.Override
    public void onHitRobot(HitRobotEvent event) {
        if (getHeading() != event.getBearing()) {
            setTurnLeft(80);
            setAhead(250);
            setTurnRight(124);
        }
        else {
            setTurnLeft(100);
            setBack(250);
            setTurnRight(56);
        }
    }


    @java.lang.Override
    public void onHitWall(HitWallEvent event) {
        if (getHeading() != event.getBearing()) {
            setTurnLeft(80);
            setAhead(250);
            setTurnRight(124);
        }
        else {
            setTurnLeft(100);
            setBack(250);
            setTurnRight(56);
        }
    }


    @java.lang.Override
    public void onScannedRobot(ScannedRobotEvent event) {

        if (isTeammate(event.getName()) == true) {
            return;
        }


        else {
            setTurnRadarRightRadians(getHeadingRadians() + event.getBearingRadians() - getRadarHeadingRadians());
            setTurnRight(event.getBearing() + 90);
            setTurnGunRightRadians(getHeadingRadians() - getGunHeadingRadians() + event.getBearingRadians());
            if (getEnergy() > 60 && event.getDistance() < 250) {
                fire(6);
            }
            else if (getEnergy() < 60 && event.getDistance() < 250) {
                fire(5);
            }
            else if  (event.getDistance() > 250 && event.getDistance() < 400) {
                fire(5);
            }
            else if (event.getDistance() > 400 && event.getDistance() < 650) {
                fire(4);
            }
            else {
                fire(3);
            }
        }
    }
}
